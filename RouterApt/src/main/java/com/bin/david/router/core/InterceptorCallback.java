package com.bin.david.router.core;

import com.bin.david.router.bean.RouterInfo;

/**
 * Created by huang on 2018/1/29.
 */

public interface InterceptorCallback{

    void interceptor(Exception e);
    void onResume(RouterInfo routerInfo);
}
