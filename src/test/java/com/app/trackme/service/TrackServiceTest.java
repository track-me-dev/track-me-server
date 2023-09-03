package com.app.trackme.service;

import com.app.trackme.domain.Location;
import com.app.trackme.domain.Track;
import com.app.trackme.dto.request.CreateTrackDTO;
import com.app.trackme.dto.request.CreateTrackRecordDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class TrackServiceTest {

    private Long trackId;

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
    @Transactional
    @DisplayName("트랙을 처음으로 생성할 때의 기록을 해당 트랙의 랭킹 1위 기록으로 저장한다.")
    void save_track_record_as_rank_1st() {

        Track track = trackService.findTrack(trackId);
        assertThat(track.getRank1stId()).isEqualTo(1L);
    }
}