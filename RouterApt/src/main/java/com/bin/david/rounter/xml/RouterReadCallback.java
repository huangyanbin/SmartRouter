package com.bin.david.rounter.xml;

import com.bin.david.rounter.bean.RouterInfo;

import java.util.Map;

/**
 * Created by huang on 2018/1/25.
 */

public interface RouterReadCallback {

    void readSuc(Map<String,RouterInfo> routerMap);

    void readFail(String failMsg);

    void readStart();
    void readEnd();

}
