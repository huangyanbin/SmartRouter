package com.bin.david.router;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bin.david.router.bean.RouterInfo;
import com.bin.david.router.bean.RouterParam;
import com.bin.david.router.config.RouterConfig;
import com.bin.david.router.core.InterceptorCallback;
import com.bin.david.router.core.RouterInterceptor;
import com.bin.david.router.exception.RouterException;
import com.bin.david.router.core.IRouterLoad;


import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Created by huang on 2018/1/25.
 */

public class RouteLoader {
    private  Map<String,RouterInfo> rootRouterMap;
    private Map<String,RouterParam[]> routerParamMap;
    private List<RouterInterceptor> interceptorList;


    public void loadRouter(final String url, final Bundle extra){

        if(rootRouterMap !=null){
            RouterInfo routerInfo =  rootRouterMap.get(url);
            if(routerInfo !=null){
                if(interceptorList !=null&&interceptorList.size() >0) {
                    for (RouterInterceptor interceptor : interceptorList) {
                        interceptor.interceptor(routerInfo, new InterceptorCallback() {
                            @Override
                            public void interceptor(Exception e){

                            }

                            @Override
                            public void onResume(RouterInfo routerInfo) {
                               if(!routerInfo.getPath().equals(url)) {
                                 loadRouter(routerInfo.getPath(),extra);
                               }else{
                                   startActitivty(routerInfo, extra);
                               }

                             }
                        });
                    }
                }else{
                    startActitivty(routerInfo,extra);
                }
            }
        }
    }

    private void startActitivty(RouterInfo routerInfo, final Bundle extra){
        Intent intent = new Intent(SmartRouter.getInstance().getContext(),
                routerInfo.getClazz());
        intent.putExtras(extra);
        SmartRouter.getInstance().getContext().startActivity(intent);
    }

    public void getParams(Context context,Intent intent){
        if(intent == null){
            return;
        }
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
            interceptorList = load.onLoadInterceptor();
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
