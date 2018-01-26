package com.bin.david.smartRouter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bin.david.router.SmartRouter;
import com.bin.david.router.annotation.Param;
import com.bin.david.router.annotation.Router;

/**
 * Created by huang on 2018/1/25.
 * 通用测试
 */
@Router(path = "/user/comm")
public class CommonActivity extends AppCompatActivity {

    @Param
    private String name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // SmartRouter.getInstance().inject(this);
        Log.e("huang",name);
    }
}
