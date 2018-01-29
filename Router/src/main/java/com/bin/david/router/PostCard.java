package com.bin.david.router;

import android.os.Bundle;

import com.bin.david.router.bean.RouterInfo;

/**
 * Created by huang on 2018/1/29.
 */

public class PostCard {

    private String path;
    private Bundle extra;
    private String name;
    private Class clazz;

    public PostCard(RouterInfo  routerInfo) {
        this.path = routerInfo.getPath();
        this.name = routerInfo.getName();
        this.clazz = routerInfo.getClazz();
    }

    public PostCard(String path, Bundle extra, String name, Class clazz) {
        this.path = path;
        this.extra = extra;
        this.name = name;
        this.clazz = clazz;
    }
}
