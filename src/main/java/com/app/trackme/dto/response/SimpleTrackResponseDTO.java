package com.app.trackme.dto.response;

import com.app.trackme.domain.Track;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleTrackResponseDTO {

    private Long id;
    private String title;
    private Double distance;
    private Double averageSlope;
    private Double lowestAltitude;
    private Double highestAltitude;

    public static SimpleTrackResponseDTO toDto(Track entity) {
        return SimpleTrackResponseDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .distance(entity.getDistance())
                .averageSlope(entity.getAverageSlope())
                .lowestAltitude(entity.getLowestAltitude())
                .highestAltitude(entity.getHighestAltitude())
                .build();
    }
}
