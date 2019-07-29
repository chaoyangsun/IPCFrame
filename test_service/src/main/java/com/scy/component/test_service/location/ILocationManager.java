package com.scy.component.test_service.location;

import android.location.Location;

import com.scy.component.mylibrary.annotation.ServiceId;

@ServiceId("LocationManager")
public interface ILocationManager {
    Location getLocation();
}
