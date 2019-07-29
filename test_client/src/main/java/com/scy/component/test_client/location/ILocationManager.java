package com.scy.component.test_client.location;


import com.scy.component.mylibrary.annotation.ServiceId;

@ServiceId("LocationManager")
public interface ILocationManager {
   Location getLocation();
}
