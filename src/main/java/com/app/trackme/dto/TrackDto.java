package com.app.trackme.dto;

import com.app.trackme.domain.Coordinate;
import com.app.trackme.domain.Track;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TrackDto {

    private Long id;
    private String title;
    private List<Coordinate> coordinates;

    public static TrackDto toDto(Track entity) {
        return TrackDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .coordinates(entity.getCoordinates())
                .build();
    }

    public static Track toEntity(TrackDto dto) {
        return Track.builder()
                .title(dto.getTitle())
                .coordinates(dto.getCoordinates())
                .build();
    }
}
