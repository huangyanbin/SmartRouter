package com.bin.david.rounter;


import com.bin.david.rounter.annotation.Param;
import com.bin.david.rounter.annotation.Router;
import com.bin.david.rounter.bean.RouterInfo;
import com.bin.david.rounter.xml.RouterWriter;
import com.bin.david.rounter.parse.RouterParser;
import com.google.auto.service.AutoService;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * Created by huang on 2017/11/16.
 */
@AutoService(Processor.class)
public class RouterProcessor extends AbstractProcessor {

    private Messager messager;
    private Elements elementUtils;
    private Filer filer;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        elementUtils = processingEnvironment.getElementUtils();
        filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnv) {
        //获取到所有的Router注解
        Set<? extends Element> routerSet = roundEnv.getElementsAnnotatedWith(Router.class);
        Set<? extends Element> paramsSet = roundEnv.getElementsAnnotatedWith(Param.class);
        RouterParser routerParser = new RouterParser();
        Map<String,RouterInfo> routerMap =  routerParser.parse(routerSet,paramsSet);
        if(routerMap !=null &&routerMap.size()>0){
            new RouterWriter().write(routerMap);
        }
        return true;

    }





    private void onError(String message, Element element) {
        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }


    private void onNote(String message, Element element) {
        //messager.printMessage(Diagnostic.Kind.OTHER, message, element);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(Router.class.getCanonicalName());
        return set;
    }
}
