package com.app.trackme.utils;

import com.app.trackme.domain.Location;

public class GeoUtils {

    static private final int EARTH_RADIUS = 6371; // Earth's radius in kilometers

    static public double calculateDistance(Location coord1, Location coord2) {
        double dLat = Math.toRadians(coord2.getLatitude() - coord1.getLatitude());
        double dLon = Math.toRadians(coord2.getLongitude() - coord1.getLongitude());

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(coord1.getLatitude()))
                * Math.cos(Math.toRadians(coord2.getLatitude()))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c * 1000;
    }
}
