package com.bin.david.rounter.bean;

import java.util.List;

/**
 * Created by huang on 2018/1/25.
 */

public class RouterInfo {

    private String name;
    private String path;
    private List<RouterParam> routerParams;

    public RouterInfo( String name, String path) {

        this.name = name;
        this.path = path;
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
}
