package com.bin.david.smartRouter;

import com.bin.david.router.core.InterceptorCallback;
import com.bin.david.router.core.RouterInterceptor;
import com.bin.david.router.annotation.Interceptor;
import com.bin.david.router.bean.RouterInfo;

/**
 * Created by huang on 2018/1/29.
 */

@Interceptor(priority = 1,name="登录拦截")
public class LoginInterceptor implements RouterInterceptor {
    @Override
    public void interceptor(RouterInfo routerInfo, InterceptorCallback callback) {
       if(!routerInfo.getPath().contains("login")) {
           routerInfo.setPath("/login");
       }
        callback.onResume(routerInfo);
    }
}
