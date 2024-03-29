package com.nsit.hack.energy.geoFencing;

import com.google.android.gms.maps.model.LatLng;
import com.nsit.hack.energy.utils.SharedPrefs;

import java.util.HashMap;

public final class Constants {

    private Constants() {
    }

    public static final String PACKAGE_NAME = "com.nsit.hack.energy";

    public static final String SHARED_PREFERENCES_NAME = PACKAGE_NAME + ".SHARED_PREFERENCES_NAME";

    public static final String GEOFENCES_ADDED_KEY = PACKAGE_NAME + ".GEOFENCES_ADDED_KEY";

    /**
     * Used to set an expiration time for a geofence. After this amount of time Location Services
     * stops tracking the geofence.
     */
    public static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;

    /**
     * For this sample, geofences expire after twelve hours.
     */
    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
            GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;
    public static final float GEOFENCE_RADIUS_IN_METERS = 1609; // 1 mile, 1.6 km

    /**
     * Map for storing information about airports in the San Francisco bay area.
     */
    public static final HashMap<String, LatLng> BAY_AREA_LANDMARKS = new HashMap<String, LatLng>();
    static {
        // San Francisco International Airport.
        double lat = (double)SharedPrefs.getPrefs("latitude",28.6091309f);
        double lon = (double) SharedPrefs.getPrefs("longitude",77.0328746f);
        BAY_AREA_LANDMARKS.put("HOME", new LatLng(lat,lon));

        // Googleplex.
        BAY_AREA_LANDMARKS.put("GOOGLE", new LatLng(37.422611,-122.0840577));
    }
}
