package com.scy.component.testipc.location;

import com.scy.component.mylibrary.annotation.ServiceId;

@ServiceId("LocationManager")
public interface ILocationManager {
    Location getLocation();
}
