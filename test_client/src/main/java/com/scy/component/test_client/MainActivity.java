package com.scy.component.test_client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.scy.component.mylibrary.IPC;
import com.scy.component.mylibrary.IPCService;
import com.scy.component.test_client.location.ILocationManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void coneect(View view) {
        IPC.connect(this, "com.scy.component.test_service",IPCService.IPCService0.class);

    }
    public void showLocation(View view) {
        //代理对象
        ILocationManager location = IPC.getInstanceWithName(IPCService.IPCService0.class, ILocationManager.class, "getDefault");

        Toast.makeText(this, "当前位置:" + location.getLocation(), Toast.LENGTH_LONG).show();
    }
}
