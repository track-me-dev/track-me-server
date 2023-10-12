package com.app.trackme.domain;

import com.app.trackme.batch.ElevationResult;
import com.app.trackme.dto.request.CreateTrackDTO;
import com.app.trackme.utils.GeoUtils;
import com.app.trackme.utils.PathUtils;
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

    @Lob private String encodedPath;
    private Double distance;
    private Double lowestAltitude;
    private Double highestAltitude;
    private Double averageSlope;
    private Long rank1stId;
    private String createdBy;

    @OneToMany(mappedBy = "track") // (default) lazy loading
    private List<TrackRecord> records;

    public static Track create(CreateTrackDTO dto) {
        return Track.builder()
                .title(dto.getTitle())
                .encodedPath(PathUtils.encode(dto.getPath()))
                .distance(dto.getDistance())
                .records(new ArrayList<>())
                .createdBy(dto.getCreatedBy())
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
                .mapToDouble(i -> {
                    double d = GeoUtils.calculateDistance(results.get(i).getLocation(), results.get(i - 1).getLocation());
                    if (d == 0D) return 0;
                    return (results.get(i).getElevation() - results.get(i - 1).getElevation()) / d;
                })
                .sum();
        this.averageSlope = sumSlopes / (results.size() - 1);
    }

}
