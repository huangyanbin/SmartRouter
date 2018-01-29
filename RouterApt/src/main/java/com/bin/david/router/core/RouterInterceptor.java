package com.bin.david.router.core;

import com.bin.david.router.bean.RouterInfo;

/**
 * Created by huang on 2018/1/29.
 */

public interface RouterInterceptor {

     void interceptor(RouterInfo routerInfo,InterceptorCallback callback);
}
