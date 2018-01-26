package com.bin.david.router.core;

import com.bin.david.router.bean.RouterInfo;
import com.bin.david.router.bean.RouterParam;
import com.bin.david.router.config.RouterConfig;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;

/**
 * Created by huang on 2018/1/26.
 */

public class RouterGenerater {

    public void generateCode(Map<String,RouterInfo> routerInfoMap, Filer filer){
        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(RouterConfig.RouterRootLoadImp)
                .addSuperinterface(IRouterLoad.class)
                .addModifiers(Modifier.PUBLIC);
        typeBuilder.addMethod(generateRouteCode(routerInfoMap));
        typeBuilder.addMethod(generateParamsCode(routerInfoMap));
        JavaFile javaFile = JavaFile.builder(RouterConfig.RouterPackage, typeBuilder.build()).build();
        try {
            javaFile.writeTo(filer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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


    private MethodSpec generateParamsCode(Map<String,RouterInfo> routerInfoMap){

        MethodSpec.Builder methodBuilder = MethodSpec
                .methodBuilder("onLoadParam")
                .addModifiers(Modifier.PUBLIC)
                .returns(Map.class);
        methodBuilder.addStatement("$T<$T,$T[]> paramMap = new $T<$T,$T[]>()", HashMap.class,String.class,RouterParam.class,
                HashMap.class,String.class,RouterParam.class);
        int position = 0;
        for(Map.Entry<String,RouterInfo>  entry : routerInfoMap.entrySet()){
            RouterInfo routerInfo = entry.getValue();
            List<RouterParam> routerParams = routerInfo.getRouterParams();
            if(routerParams !=null &&routerParams.size()>0) {
                String paramsName = "params"+position;
                methodBuilder.addStatement("$T[] $N = new $T[$L]", RouterParam.class,paramsName,RouterParam.class,routerParams.size());
                for(int i =0;i<routerParams.size();i++) {
                    RouterParam param = routerParams.get(i);
                    methodBuilder.addStatement("$N[$L]= new $T($S,$S,$S,$S)",paramsName,i,RouterParam.class,param.getName(),param.getExtraName(),param.getClazzName(),param.getTypeName());
                }
                methodBuilder.addStatement("paramMap.put($S,$N)", routerInfo.getName(),paramsName);
                position++;
            }
        }
        methodBuilder.addStatement("return paramMap");
        return methodBuilder.build();

    }

}
