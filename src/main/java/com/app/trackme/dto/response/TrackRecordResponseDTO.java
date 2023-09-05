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

    public static TrackRecordResponseDTO toDto(TrackRecord trackRecord) {
        return TrackRecordResponseDTO.builder()
                .id(trackRecord.getId())
                .time(trackRecord.getTime())
                .build();
    }

}
