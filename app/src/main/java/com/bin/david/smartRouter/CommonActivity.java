package com.bin.david.smartRouter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bin.david.rounter.annotation.Param;
import com.bin.david.rounter.annotation.Router;

/**
 * Created by huang on 2018/1/25.
 * 通用测试
 */
@Router(path = "/user/comm")
public class CommonActivity extends AppCompatActivity {

    @Param
    private String param1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
