package com.bin.david.router;

import android.content.Context;
import android.content.Intent;

import com.bin.david.router.bean.RouterInfo;
import com.bin.david.router.bean.RouterParam;
import com.bin.david.router.config.RouterConfig;
import com.bin.david.router.exception.RouterException;
import com.bin.david.router.core.IRouterLoad;


import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by huang on 2018/1/25.
 */

public class RouteLoader {
    private  Map<String,RouterInfo> rootRouterMap;
    private Map<String,RouterParam[]> routerParamMap;


    public Class getRouter(String url){
        if(rootRouterMap !=null){
            return rootRouterMap.get(url).getClazz();
        }
        return null;
    }

    public void getParams(Context context,Intent intent){
        String className = context.getClass().getName();
        RouterParam[] params = routerParamMap.get(className);
        if(params !=null){
            Field field;
            try {
                for(RouterParam param :params){
                    if(intent.getExtras() !=null) {
                        Object extra = intent.getExtras().get(param.getExtraName());
                        if (extra != null) {
                            field = context.getClass().getDeclaredField(param.getName());
                            field.setAccessible(true);
                            field.set(context, extra);
                        }
                    }
                }
            } catch (NoSuchFieldException e) {
                throw new  RouterException("NoSuchFieldException no found this field");
            } catch (IllegalAccessException e) {
                throw new  RouterException("IllegalAccessException params Extra Type inconsistency");
            }
        }
    }

    public void loadRootRouter(){
        if(rootRouterMap ==null) {
            IRouterLoad load = create(RouterConfig.RouterRootLoadImp);
            rootRouterMap = load.onLoadRoute();
            routerParamMap = load.onLoadParam();
        }
    }

    public IRouterLoad create(String clazzName) {
        String impClazz = RouterConfig.RouterPackage+"."+clazzName;
        try {
            Class childClazz = Class.forName(impClazz);
            return (IRouterLoad)childClazz.newInstance();
        }catch (ClassNotFoundException e){
            throw new RouterException("ClassNotFoundException "+impClazz);
        } catch (IllegalAccessException e) {
            throw new RouterException("IllegalAccessException "+impClazz);
        } catch (InstantiationException e) {
            throw new RouterException("InstantiationException "+impClazz);
        }
    }
}
