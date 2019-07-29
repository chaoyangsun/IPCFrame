package com.scy.component.test_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.scy.component.mylibrary.IPC;
import com.scy.component.test_service.location.Location;
import com.scy.component.test_service.location.LocationManager;

/**
 * 服务进程
 */
public class GpsService extends Service {
    public GpsService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //模拟定位
        LocationManager.getDefault().setLocation(new Location("岳麓区天之道", 1.1d, 2.2d));

        //将数据携带者（LocationManager）注册到框架
        IPC.regist(LocationManager.class);
    }
}
