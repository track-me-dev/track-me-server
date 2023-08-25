package com.app.trackme.dto.request;

import com.app.trackme.domain.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateTrackDTO {

    private String title;
    private List<Coordinate> path;
    private Double distance;
    private CreateTrackRecordDTO trackRecord;
}
