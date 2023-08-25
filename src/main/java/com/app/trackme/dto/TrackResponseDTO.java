package com.app.trackme.dto;

import com.app.trackme.domain.Coordinate;
import com.app.trackme.domain.Track;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TrackResponseDTO {

    private Long id;
    private String title;
    private List<Coordinate> path;
    private Double distance;
    private Double averageSlope;
    private Double lowestAltitude;
    private Double highestAltitude;

    public static TrackResponseDTO toDto(Track entity) {
        return TrackResponseDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .path(entity.getPath())
                .distance(entity.getDistance())
                .averageSlope(entity.getAverageSlope())
                .lowestAltitude(entity.getLowestAltitude())
                .highestAltitude(entity.getHighestAltitude())
                .build();
    }
}
