package com.app.trackme.dto.request;

import com.app.trackme.domain.Location;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTrackRecordDTO {

    private List<Location> path;
    private Double distance;
    private Double time;
    private String username;

}
