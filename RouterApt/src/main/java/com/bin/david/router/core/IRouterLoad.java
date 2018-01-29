package com.bin.david.router.core;

import java.util.List;
import java.util.Map;

/**
 * Created by huang on 2018/1/25.
 */

public interface IRouterLoad {

     Map onLoadRoute();
     Map onLoadParam();

     List onLoadInterceptor();

}
