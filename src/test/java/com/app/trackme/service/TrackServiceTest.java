package com.app.trackme.service;

import com.app.trackme.domain.Location;
import com.app.trackme.domain.Track;
import com.app.trackme.domain.TrackRecord;
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

    @Test
    @DisplayName("생성된 트랙에 새로운 기록을 추가할 때 기존 1위의 기록보다 빠르면 1위 기록을 업데이트한다.")
    void update_rank_1st_if_new_record_is_faster_than_existing() {
        Long existingRecordId = trackService.findTrack(trackId).getRank1stId();
        List<Location> path = List.of(
                new Location(37.512583, 126.960039, 0.0),
                new Location(37.512480, 126.960226, 0.5),
                new Location(37.512377, 126.960412, 1.0)
        );
        CreateTrackRecordDTO trackRecordDTO = CreateTrackRecordDTO.builder()
                .path(path)
                .distance(100.0)
                .time(1.0)
                .build();

        trackService.createTrackRecord(trackId, trackRecordDTO);

        Track track = trackService.findTrack(trackId);
        Long newRecordId = track.getRank1stId();
        assertThat(newRecordId).isNotEqualTo(existingRecordId);
    }

    @Test
    @DisplayName("생성된 트랙에 새로운 기록을 추가할 때 기존 1위의 기록보다 느리면 1위 기록을 유지한다.")
    void keep_rank_1st_if_new_record_is_slower_than_existing() {
        Long existingRecordId = trackService.findTrack(trackId).getRank1stId();
        List<Location> path = List.of(
                new Location(37.512583, 126.960039, 0.0),
                new Location(37.512480, 126.960226, 2.0),
                new Location(37.512377, 126.960412, 4.0)
        );
        CreateTrackRecordDTO trackRecordDTO = CreateTrackRecordDTO.builder()
                .path(path)
                .distance(100.0)
                .time(4.0)
                .build();

        trackService.createTrackRecord(trackId, trackRecordDTO);

        Track track = trackService.findTrack(trackId);
        Long newRecordId = track.getRank1stId();
        assertThat(newRecordId).isEqualTo(existingRecordId);
    }
}