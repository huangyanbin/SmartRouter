package com.bin.david.router;

import android.app.Activity;
import android.app.Service;
import android.content.Context;

import com.bin.david.rounter.bean.RouterInfo;
import com.bin.david.rounter.exception.RounterException;
import com.bin.david.rounter.xml.RouterReadCallback;
import com.bin.david.rounter.xml.RouterReader;

import java.util.Map;

/**
 * Created by huang on 2018/1/25.
 */

public class SmartRouter {

    private Context mContext;
    private static SmartRouter mInstance;
    private RouterReader reader;

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
        reader = new RouterReader();
        reader.read("router", new RouterReadCallback() {
            @Override
            public void readSuc(Map<String, RouterInfo> routerMap) {

            }

            @Override
            public void readFail(String failMsg) {

            }

            @Override
            public void readStart() {

            }

            @Override
            public void readEnd() {

            }
        });
    }

    public static SmartRouter getInstance(){
        if(mInstance ==null){
            throw new RounterException("SmartRouter is not init");
        }
        return mInstance;
    }


}
