package com.app.trackme.batch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElevationResult {

    private Double elevation;
    private Location location;

    public com.app.trackme.domain.Location getLocation() {
        return new com.app.trackme.domain.Location(location.lat, location.lng);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Location {
        private Double lat;
        private Double lng;
    }
}
