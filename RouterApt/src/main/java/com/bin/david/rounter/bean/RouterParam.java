package com.bin.david.rounter.bean;

/**
 * Created by huang on 2018/1/25.
 */

public class RouterParam {

    private String name;
    private String clazzName;

    public RouterParam(String name, String clazzName) {
        this.name = name;
        this.clazzName = clazzName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }
}
