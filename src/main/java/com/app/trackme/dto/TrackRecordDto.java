package com.app.trackme.dto;

import com.app.trackme.domain.TrackRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TrackRecordDto {

    private Long id;
    private Double distance;
    private Double time;
    private Double lowestAltitude;
    private Double highestAltitude;
    private Double averageSlope;

    public static TrackRecordDto toDto(TrackRecord entity) {
        return TrackRecordDto.builder()
                .id(entity.getId())
                .time(entity.getTime())
                .build();
    }

}
