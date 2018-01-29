package com.bin.david.router.bean;

import java.util.List;

/**
 * Created by huang on 2018/1/25.
 */

public class RouterInfo {


    private String name;
    private String path;
    private Class clazz;
    private List<RouterParam> routerParams;

    public RouterInfo( String name, String path) {

        this.name = name;
        this.path = path;
    }

    public RouterInfo(Class clazz,String name, String path) {
        this.name = name;
        this.path = path;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<RouterParam> getRouterParams() {
        return routerParams;
    }

    public void setRouterParams(List<RouterParam> routerParams) {
        this.routerParams = routerParams;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
