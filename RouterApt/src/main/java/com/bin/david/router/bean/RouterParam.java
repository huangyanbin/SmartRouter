package com.bin.david.router.bean;

/**
 * Created by huang on 2018/1/25.
 */

public class RouterParam {

    private String name;
    private String clazzName;
    private String extraName;
    private String typeName;
    public RouterParam(String name,String extraName,String clazzName,String typeName) {
        this.name = name;
        this.clazzName = clazzName;
        this.extraName = extraName;
        this.typeName = typeName;
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

    public String getExtraName() {
        return extraName;
    }

    public void setExtraName(String extraName) {
        this.extraName = extraName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
