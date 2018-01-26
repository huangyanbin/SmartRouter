package com.bin.david.router;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.IBinder;

/**
 * Created by huang on 2018/1/26.
 */

public class InstrumentationProxy extends Instrumentation {

    private  Instrumentation mInstrumentation;

    public InstrumentationProxy(Instrumentation mInstrumentation) {
        this.mInstrumentation = mInstrumentation;
    }

    @Override
    public Activity newActivity(ClassLoader cl, String className, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Activity activity =  mInstrumentation.newActivity(cl, className, intent);
        SmartRouter.getInstance().inject(activity,intent);
        return activity;
    }

    @Override
    public Activity newActivity(Class<?> clazz, Context context, IBinder token, Application application, Intent intent, ActivityInfo info, CharSequence title, Activity parent, String id, Object lastNonConfigurationInstance) throws InstantiationException, IllegalAccessException {
        Activity activity =  mInstrumentation.newActivity(clazz, context, token, application, intent, info, title, parent, id, lastNonConfigurationInstance);
        SmartRouter.getInstance().inject(activity,intent);
        return activity;
    }
}
