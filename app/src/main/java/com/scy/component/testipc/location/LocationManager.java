package com.scy.component.testipc.location;

import com.scy.component.mylibrary.annotation.ServiceId;

/**
 * @author sunchaoyang
 */
@ServiceId("LocationManager")
public class LocationManager {
    private static final LocationManager OUR_INSTANCE = new LocationManager();

    public static LocationManager getDefault() {
        return OUR_INSTANCE;
    }

    private LocationManager(){}

    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
