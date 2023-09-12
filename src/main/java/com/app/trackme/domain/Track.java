package com.app.trackme.domain;

import com.app.trackme.batch.ElevationResult;
import com.app.trackme.dto.request.CreateTrackDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRACK_ID")
    private Long id;

    private String title;

    @ElementCollection // (default) lazy loading
    @CollectionTable(
            name = "LOCATION_FOR_TRACK",
            joinColumns = @JoinColumn(name = "TRACK_ID")
    )
    private List<Location> path;
    private Double distance;
    private Double lowestAltitude;
    private Double highestAltitude;
    private Double averageSlope;
    private Long rank1stId;

    @OneToMany(mappedBy = "track") // (default) lazy loading
    private List<TrackRecord> records;

    public static Track create(CreateTrackDTO dto) {
        return Track.builder()
                .title(dto.getTitle())
                .path(dto.getPath())
                .distance(dto.getDistance())
                .records(new ArrayList<>())
                .build();
    }

    public void updateRank1st(Long trackRecordId) {
        this.rank1stId = trackRecordId;
    }

    public void setElevationResult(List<ElevationResult> results) {
        this.lowestAltitude = results.stream()
                .mapToDouble(ElevationResult::getElevation)
                .min().getAsDouble();
        this.highestAltitude = results.stream()
                .mapToDouble(ElevationResult::getElevation)
                .max().getAsDouble();
        double sumSlopes = IntStream.range(1, results.size())
                .mapToDouble(i ->
                        (results.get(i).getElevation() - results.get(i - 1).getElevation())
                                / calculateDistance(results.get(i).getLocation(), results.get(i - 1).getLocation()))
                .sum();
        this.averageSlope = sumSlopes / (results.size() - 1);
    }


    // TODO: 클래스 분리
    private final int EARTH_RADIUS = 6371; // Earth's radius in kilometers

    public double calculateDistance(ElevationResult.Location coord1, ElevationResult.Location coord2) {
        double dLat = Math.toRadians(coord2.getLat() - coord1.getLat());
        double dLon = Math.toRadians(coord2.getLng() - coord1.getLng());

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(coord1.getLat()))
                * Math.cos(Math.toRadians(coord2.getLat()))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c * 1000;
    }
}
