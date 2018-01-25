package com.bin.david.router;

import com.bin.david.router.bean.RouterInfo;
import com.bin.david.router.config.RouterConfig;
import com.bin.david.router.exception.RounterException;
import com.bin.david.router.parse.IRouterLoad;

import java.util.Map;

/**
 * Created by huang on 2018/1/25.
 */

public class RouteLoader {
    private  Map<String,RouterInfo> rootRouterMap;


    public Class getRouter(String url){
        if(rootRouterMap !=null){
            return rootRouterMap.get(url).getClazz();
        }
        return null;
    }

    public void loadRootRouter(){
        if(rootRouterMap ==null) {
            IRouterLoad load = create(RouterConfig.RouterRootLoadImp);
            rootRouterMap = load.onLoad();
        }
    }

    public  IRouterLoad create(String clazzName) {
        String impClazz = RouterConfig.RouterPackage+"."+clazzName;
        try {
            Class childClazz = Class.forName(impClazz);
            return (IRouterLoad)childClazz.newInstance();
        }catch (ClassNotFoundException e){
            throw new RounterException("ClassNotFoundException "+impClazz);
        } catch (IllegalAccessException e) {
            throw new RounterException("IllegalAccessException "+impClazz);
        } catch (InstantiationException e) {
            throw new RounterException("InstantiationException "+impClazz);
        }
    }
}
