# SmartRouter
仿阿里ARouter ---android router

##### 原因
> 之前用编译注解写了```Retrofit```简易版本，在网上看见了```ARouter```使用，觉得我也可以撸一个相对简单点的，希望能坚持下去。

###### 第一天

> 首先重写AbstractProcessor类，获取注解信息：

```
 @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnv) {
        //获取到所有的Router注解
        Set<? extends Element> routerSet = roundEnv.getElementsAnnotatedWith(Router.class);
        Set<? extends Element> paramsSet = roundEnv.getElementsAnnotatedWith(Param.class);
        RouterParser routerParser = new RouterParser();
        Map<String,RouterInfo> routerMap =  routerParser.parse(routerSet,paramsSet);
       // if(routerMap !=null &&routerMap.size()>0){
            new RouterGenerater().generateCode(routerMap,filer);
        //}
        return true;

    }
```

> 这里遇到一个坑，我开始想```Activity```和```Service```参数跳转这些信息这么多保存在```xml```中。我把要写的信息放入```Map<String,RouterInfo>```中，然后用SAX生成xml.SmartRouter init 调用初始化下信息。之后可以根据```Group```，可以分批读取。我自己都被自己想法陶醉了。


```
<?xml version="1.0" encoding="UTF-8"?>
<routerStore>
    <router name="com.bin.david.smartRouter.CommonActivity">
        <path>/user/comm</path>
    </router>
</routerStore>
```
> 写的差不多发现一个问题，就是```xml```放哪的问题，```android``` 支持打包进去有 ```assets```,```res/raw```,```res/xml``` xml放入路径未知。尝试失败后，放弃！转生成java文件。

###### 第二天

> 通过```javapoet```生成用于初始化路由代码，下面是```javapoet```生成路由代码：

```
    private MethodSpec generateRouteCode(Map<String,RouterInfo> routerInfoMap){

        MethodSpec.Builder methodBuilder = MethodSpec
                .methodBuilder("onLoadRoute")
                .addModifiers(Modifier.PUBLIC)
                .returns(Map.class);
        methodBuilder.addStatement("$T<$T,$T> routerMap = new $T<$T,$T>()", HashMap.class,String.class,RouterInfo.class,
                HashMap.class,String.class,RouterInfo.class);
        for(Map.Entry<String,RouterInfo>  entry : routerInfoMap.entrySet()){
            RouterInfo routerInfo = entry.getValue();
            methodBuilder.addStatement("routerMap.put($S,new RouterInfo($N.class,$S,$S))",routerInfo.getPath(),routerInfo.getName(),routerInfo.getName(),routerInfo.getPath());
        }
        methodBuilder.addStatement("return routerMap");
        return methodBuilder.build();

    }
```
> 然后用建造模式用于构建```Intent```信息；

```
  public static class Builder{
        private String url;
        private Intent intent;

        private Builder(String url) {
            this.url = url;
            intent = new Intent();
        }

        public void navigation(){
            SmartRouter.getInstance().start(this);
        }

        public Builder withString(String key,String value){
            intent.putExtra(key,value);
            return this;
        }
        ...
```
> 使用静态代理代理```Instrumentation```对象，并在```newActivity```方法调用 ```SmartRouter.getInstance().inject(activity,intent);```帮助```activity``` 自动注入传参。

```
 /**
     * 自动注入Actvity参数值
     * 在Application开启之后不需要在Activity调用inject方法了
     */
    public void routerAutoInject(Application application){
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread",false,application.getClassLoader());
            Method method = activityThreadClass.getDeclaredMethod("currentActivityThread");
            Object currentActivityThread = method.invoke(null); //静态方法
            Field field = activityThreadClass.getDeclaredField("mInstrumentation");
            field.setAccessible(true);
            Instrumentation instrumentation = (Instrumentation) field.get(currentActivityThread);
            InstrumentationProxy instrumentationProxy = new InstrumentationProxy(instrumentation);
            field.set(currentActivityThread, instrumentationProxy);
        } catch (Exception e) {
            //throw new RouterException("routerAutoInject failure");
        }
    }
```

###### 第三天
> 在网上看到这两张图，我采用的就是那种不稳定的做法，稳定我之前也想过，只是担心在编译之后会产生大量的类。![image](/img/instrumentation.png)![image](/img/new_activity_field.png)









