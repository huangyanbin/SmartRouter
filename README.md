# SmartRouter
android router

##### 原因
> 之前用编译注解写了Retrofit简易版本，在网上看见了ARouter，觉得我也可以撸一个相对简单点的，希望能坚持下去。

###### 第一天
> 今天做完了，简单逻辑基本写完了，结果遇到一个坑，我开始想Activity和Service参数跳转这些信息这么多保存在xml中。我把要写的信息放入Map<String,RouterInfo>中，然后用SAX生成xml.SmartRouter init 调用初始化下信息。之后可以根据Group，可以分批读取。我自己都被自己想法陶醉了。

```
<?xml version="1.0" encoding="UTF-8"?>
<routerStore>
    <router name="com.bin.david.smartRouter.CommonActivity">
        <path>/user/comm</path>
    </router>
</routerStore>
```
> 写的差不多发现一个问题，就是xml放哪的问题，android 支持打包进去有 assets,res/raw,res/xml 路径未知。尝试失败后，放弃！转生成java文件。