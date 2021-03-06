package com.bin.david.router;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.bin.david.router.exception.RouterException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * Created by huang on 2018/1/25.
 */

public class SmartRouter {

    private Context mContext;
    private static SmartRouter mInstance;
    private RouteLoader mRouterLoader;

    public static void init(Context context){
        if(context ==null){
            throw new RouterException("SmartRouter init param context is null");
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
            throw new RouterException("SmartRouter is not init");
        }
        return mInstance;
    }

    public Builder build(String url){
        return new Builder(url);
    }

    private void start(Builder builder){
         mRouterLoader.loadRouter(builder.url,builder.extra);
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * 自动注入Actvity参数值
     * 在Application开启之后不需要在Activity调用inject方法了
     */
    public void routerAutoInject(Application application){
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread",false,application.getClassLoader());
            Method method = activityThreadClass.getDeclaredMethod("currentActivityThread");
            Object currentActivityThread = method.invoke(null); //静态方法
            Field field = activityThreadClass.getDeclaredField("mInstrumentation");
            field.setAccessible(true);
            Instrumentation instrumentation = (Instrumentation) field.get(currentActivityThread);
            InstrumentationProxy instrumentationProxy = new InstrumentationProxy(instrumentation);
            field.set(currentActivityThread, instrumentationProxy);
        } catch (Exception e) {
            //throw new RouterException("routerAutoInject failure");
        }
    }

    public void inject(Activity activity){
       inject(activity,activity.getIntent());
    }


    public void inject(Activity activity,Intent intent){
        mRouterLoader.getParams(activity,intent);
    }

    public static class Builder{
        private String url;
        private Bundle extra;

        private Builder(String url) {
            this.url = url;
            extra = new Bundle();
        }

        public void navigation(){
            SmartRouter.getInstance().start(this);
        }

        public Builder withString(String key,String value){
            extra.putString(key,value);

            return this;
        }

        public Builder withInt(String key,int value){
            extra.putInt(key,value);
            return this;
        }
        public Builder withLong(String key,long value){
            extra.putLong(key,value);
            return this;
        }

        public Builder withShort(String key,short value){
            extra.putShort(key,value);
            return this;
        }

        public Builder widthParcelable(String key,Parcelable value){
            extra.putParcelable(key,value);
            return this;
        }

        public Bundle getExtra(){
            return extra;
        }



    }

}
