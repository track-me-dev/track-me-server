package com.app.trackme.dto.response;

import com.app.trackme.domain.Location;
import com.app.trackme.domain.Track;
import com.app.trackme.domain.TrackRecord;
import com.app.trackme.utils.PathUtils;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackViewResponseDTO {

    private Long id;
    private String title;
    private Double distance;
    private Double averageSlope;
    private Double lowestAltitude;
    private Double highestAltitude;
    private Double rank1stTime;
    private List<Location> path;


    public static TrackViewResponseDTO toDto(Track track, TrackRecord rank1stRecord) {
        return TrackViewResponseDTO.builder()
                .id(track.getId())
                .title(track.getTitle())
                .distance(track.getDistance())
                .averageSlope(track.getAverageSlope())
                .lowestAltitude(track.getLowestAltitude())
                .highestAltitude(track.getHighestAltitude())
                .rank1stTime(rank1stRecord.getTime())
                .path(PathUtils.decode(track.getEncodedPath()))
                .build();
    }
}
