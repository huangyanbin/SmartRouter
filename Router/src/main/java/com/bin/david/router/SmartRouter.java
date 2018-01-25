package com.bin.david.router;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import com.bin.david.router.bean.RouterInfo;
import com.bin.david.router.exception.RounterException;

import java.util.Map;

/**
 * Created by huang on 2018/1/25.
 */

public class SmartRouter {

    private Context mContext;
    private static SmartRouter mInstance;
    private RouteLoader mRouterLoader;

    public static void init(Context context){
        if(context ==null){
            throw new RounterException("SmartRouter init param context is null");
        }
        if(mInstance == null){
            synchronized (SmartRouter.class) {
                if(context instanceof Activity){
                    context = ((Activity)context).getApplication();
                }
                if(context instanceof Service){
                    context = ((Service)context).getApplication();
                }
                mInstance = new SmartRouter(context);
            }
        }
    }

    private SmartRouter(Context context){
        this.mContext = context;
        mRouterLoader = new RouteLoader();
        mRouterLoader.loadRootRouter();
    }

    public static SmartRouter getInstance(){
        if(mInstance ==null){
            throw new RounterException("SmartRouter is not init");
        }
        return mInstance;
    }

    public Builder build(String url){
        return new Builder(url);
    }

    private void start(Builder builder){
        Class clazz = mRouterLoader.getRouter(builder.url);
        Intent intent = new Intent(mContext,clazz);
        mContext.startActivity(intent);
    }

    public static class Builder{
        private String url;

        public Builder(String url) {
            this.url = url;
        }

        public void navigation(){
            SmartRouter.getInstance().start(this);
        }
    }

}
