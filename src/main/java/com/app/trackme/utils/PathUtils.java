package com.app.trackme.utils;

import com.app.trackme.domain.Location;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.LatLng;

import java.util.List;

public class PathUtils {

    static public String encode(List<Location> path) {
        return PolylineEncoding.encode(path.stream()
                .map(l -> new LatLng(l.getLatitude(), l.getLongitude()))
                .toList());
    }

    static public List<Location> decode(String encodedPath) {
        return PolylineEncoding.decode(encodedPath).stream()
                .map(l -> new Location(l.lat, l.lng))
                .toList();
    }
}
