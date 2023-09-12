package com.app.trackme.controller;

import com.app.trackme.domain.Location;
import com.app.trackme.dto.request.CreateTrackDTO;
import com.app.trackme.dto.request.CreateTrackRecordDTO;
import com.app.trackme.dto.response.TrackRecordResponseDTO;
import com.app.trackme.service.TrackService;
import com.app.trackme.utils.TestUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
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
    @Autowired
    private TestUtils testUtils;

    @BeforeEach
    void init() {
        // 최초의 주행 트랙 생성
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

        // 생성된 주행 트랙에 기록 더하기
        Random random = new Random();
        IntStream.range(0, 10)
                .forEach(i -> {
                    CreateTrackRecordDTO dto = CreateTrackRecordDTO.builder()
                            .path(path)
                            .distance(100.0)
                            .time((double) (random.nextInt(20) + 1))
                            .build();
                    trackService.createTrackRecord(trackId, dto);
                });
    }

    @AfterEach
    void rollback() {
        testUtils.rollback();
    }

    @Test
    @DisplayName("trackId를 통해 track을 조회할 때 path가 포함되어 있어야 한다.")
    void must_contain_both_path_and_records_in_a_track() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/tracks/{trackId}", trackId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.path.length()").value(3));
    }

    @Test
    @DisplayName("트랙에 있는 모든 기록을 조회할 때 size=5로 paging하고, 주행 시간에 따라 정렬해서 조회한다.")
    void retrieve_track_records_with_pagination_and_order_by_time() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/tracks/{trackId}/records", trackId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(5))
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        String content = mapper.readTree(json).get("content").toString();
        List<TrackRecordResponseDTO> response = mapper.readValue(content, new TypeReference<>() {
        });

        assertThat(response).isSortedAccordingTo(Comparator.comparingDouble(TrackRecordResponseDTO::getTime));
    }

    @Test
    @DisplayName("트랙에 있는 기록을 조회할 때 마지막 페이지로 조회하면 남은 1개의 데이터만 조회된다.")
    void retrieve_the_last_record_with_last_page() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tracks/{trackId}/records", trackId)
                        .param("page", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andReturn();
    }
}