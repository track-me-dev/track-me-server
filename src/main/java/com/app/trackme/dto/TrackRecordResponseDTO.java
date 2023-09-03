package com.app.trackme.dto;

import com.app.trackme.domain.TrackRecord;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackRecordResponseDTO {

    private Long id;
    private Double distance;
    private Double time;
    private Double lowestAltitude;
    private Double highestAltitude;
    private Double averageSlope;

    public static TrackRecordResponseDTO toDto(TrackRecord entity) {
        return TrackRecordResponseDTO.builder()
                .id(entity.getId())
                .time(entity.getTime())
                .build();
    }

}
