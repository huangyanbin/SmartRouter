package com.bin.david.router.parse;

import com.bin.david.router.annotation.Param;
import com.bin.david.router.annotation.Router;
import com.bin.david.router.bean.RouterInfo;
import com.bin.david.router.bean.RouterParam;
import com.bin.david.router.config.RouterConfig;
import com.google.common.reflect.TypeToken;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * Created by huang on 2018/1/25.
 */

public class RouterParser {

    public Map<String,RouterInfo> parse(Set<? extends Element> routerSet,Set<? extends Element> paramsSet){
        Map<String,RouterInfo> routerMap = parseRouters(routerSet);
        Map<String,List<RouterParam>> paramsMap = parseParams(paramsSet);
        if(routerMap!=null && routerMap.size() >0){
            if(paramsMap !=null && paramsMap.size()>0) {
                for (Map.Entry<String, RouterInfo> routerEntry : routerMap.entrySet()) {
                        String className = routerEntry.getKey();
                        RouterInfo routerInfo = routerEntry.getValue();
                        List<RouterParam> routerParams =  paramsMap.get(className);
                        if(routerParams !=null){
                            routerInfo.setRouterParams(routerParams);
                        }
                }
            }
        }
        return routerMap;
    }

    public Map<String,RouterInfo> parseRouters(Set<? extends Element> routerSet){
        HashMap<String,RouterInfo> routerInfoMap = new HashMap<>();

        for (Element e : routerSet) {
            if (e.getKind() != ElementKind.CLASS) {
               // onError("Builder annotation can only be applied to method", e);
                continue;
            }
            TypeElement element = (TypeElement) e;
            String className = element.getQualifiedName().toString();
            Router router = element.getAnnotation(Router.class);
            String path = router.path();
            routerInfoMap.put(className,new RouterInfo(className,path));

        }
        return routerInfoMap;
    }

    public Map<String,List<RouterParam>> parseParams(Set<? extends Element> paramsSet){
        HashMap<String,List<RouterParam>> routerParamMap = new HashMap<>();
        for (Element e : paramsSet) {
            if (e.getKind() != ElementKind.FIELD) {
                // onError("Builder annotation can only be applied to method", e);
                continue;
            }
            VariableElement element = (VariableElement) e;
            String fieldName = element.getSimpleName().toString();
            Object o  = element.getConstantValue();
            TypeElement classElement = (TypeElement) element.getEnclosingElement();
            String className = classElement.getQualifiedName().toString();
            Param paramAnnotation = element.getAnnotation(Param.class);
            String name = "".equals(paramAnnotation.name())?fieldName:paramAnnotation.name();
            RouterParam routerParam = new RouterParam(name,"");
            List<RouterParam> params = routerParamMap.get(className);
            if(params !=null){
                params.add(routerParam);
            }else{
                params = new ArrayList<>();
                params.add(routerParam);
                routerParamMap.put(className,params);
            }
        }
        return routerParamMap;
    }


    public void generateCode( Map<String,RouterInfo> routerInfoMap,Filer filer){

        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(RouterConfig.RouterRootLoadImp)
                .addSuperinterface(IRouterLoad.class)
                .addModifiers(Modifier.PUBLIC);
        MethodSpec.Builder methodBuilder = MethodSpec
                .methodBuilder("onLoad")
                .addModifiers(Modifier.PUBLIC)
                .returns(Map.class);
        methodBuilder.addStatement("$T<$T,$T> routerMap = new $T<$T,$T>()", LinkedHashMap.class,String.class,RouterInfo.class,
                LinkedHashMap.class,String.class,RouterInfo.class);
        for(Map.Entry<String,RouterInfo>  entry : routerInfoMap.entrySet()){
            RouterInfo routerInfo = entry.getValue();
            methodBuilder.addStatement("routerMap.put($S,new RouterInfo($N.class,$S,$S))",routerInfo.getPath(),routerInfo.getName(),routerInfo.getName(),routerInfo.getPath());
        }
        methodBuilder.addStatement("return routerMap");
        typeBuilder.addMethod(methodBuilder.build());
        JavaFile javaFile = JavaFile.builder(RouterConfig.RouterPackage, typeBuilder.build()).build();
        try {
            javaFile.writeTo(filer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
