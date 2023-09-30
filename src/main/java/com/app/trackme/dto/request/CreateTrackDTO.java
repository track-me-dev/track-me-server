package com.app.trackme.dto.request;

import com.app.trackme.domain.Location;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTrackDTO {

    private String title;
    private List<Location> path;
    private Double distance;
    private CreateTrackRecordDTO trackRecord;
    private String createdBy;
}
