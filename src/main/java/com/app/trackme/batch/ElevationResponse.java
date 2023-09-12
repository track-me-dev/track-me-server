package com.app.trackme.batch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElevationResponse {

    private List<ElevationResult> results;
    private ElevationStatus status;
}
