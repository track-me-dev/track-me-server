package com.app.trackme.controller;

import com.app.trackme.domain.Location;
import com.app.trackme.dto.request.CreateTrackDTO;
import com.app.trackme.dto.request.CreateTrackRecordDTO;
import com.app.trackme.service.TrackService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TrackControllerTest {

    private final ObjectMapper mapper = new ObjectMapper();
    private Long trackId;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TrackService trackService;

    @BeforeEach
    void init() {
        List<Location> path = List.of(
                new Location(37.512583, 126.960039, 0.0),
                new Location(37.512480, 126.960226, 1.0),
                new Location(37.512377, 126.960412, 2.0)
        );
        CreateTrackRecordDTO trackRecordDTO = CreateTrackRecordDTO.builder()
                .path(path)
                .distance(100.0)
                .time(2.0)
                .build();

        CreateTrackDTO trackDTO = CreateTrackDTO.builder()
                .title("track01")
                .path(path)
                .distance(100.0)
                .trackRecord(trackRecordDTO)
                .build();
        trackId = trackService.createTrack(trackDTO);
    }

    @Test
    @DisplayName("trackId를 통해 track을 조회할 때 path와 records가 포함되어 있어야 한다.")
    void must_contain_both_path_and_records_in_a_track() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/tracks/{trackId}", trackId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(trackId))
                .andExpect(jsonPath("$.title").value("track01"))
                .andExpect(jsonPath("$.path.length()").value(3))
                .andExpect(jsonPath("$.distance").value(100.0));
    }

}