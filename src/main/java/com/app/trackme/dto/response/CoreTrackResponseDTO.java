package com.app.trackme.dto.response;

import com.app.trackme.domain.Location;
import com.app.trackme.domain.Track;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoreTrackResponseDTO {

    private List<Location> path;
    private List<TrackRecordResponseDTO> records;

    public static CoreTrackResponseDTO toDto(Track track) {
        return CoreTrackResponseDTO.builder()
                .path(track.getPath())
                .records(track.getRecords().stream()
                        .map(TrackRecordResponseDTO::toDto)
                        .toList())
                .build();
    }
}
