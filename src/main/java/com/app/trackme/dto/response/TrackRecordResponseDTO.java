package com.app.trackme.dto.response;

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
    private String username;

    public static TrackRecordResponseDTO toDto(TrackRecord trackRecord) {
        return TrackRecordResponseDTO.builder()
                .id(trackRecord.getId())
                .distance(trackRecord.getDistance())
                .time(trackRecord.getTime())
                .username(trackRecord.getUsername())
                .build();
    }

}
