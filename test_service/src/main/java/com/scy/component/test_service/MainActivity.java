package com.scy.component.test_service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.scy.component.mylibrary.IPC;
import com.scy.component.mylibrary.IPCService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, GpsService.class));
        IPC.connect(this, IPCService.IPCService0.class);
    }
}
