package com.bin.david.smartRouter;

import android.app.Application;
import android.content.Context;

import com.bin.david.router.SmartRouter;

/**
 * Created by huang on 2018/1/25.
 */

public class RouterApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        SmartRouter.init(base);

    }
}
