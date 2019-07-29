package com.scy.component.testipc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.scy.component.mylibrary.IPC;
import com.scy.component.mylibrary.IPCService;
import com.scy.component.testipc.location.ILocationManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, GpsService.class));

        IPC.connect(this, IPCService.IPCService0.class);

    }
    public void showLocation(View view) {
        //代理对象
        ILocationManager location = IPC.getInstanceWithName(IPCService.IPCService0.class, ILocationManager.class, "getDefault");

        Toast.makeText(this, "当前位置:" + location.getLocation(), Toast.LENGTH_LONG).show();
    }
}
