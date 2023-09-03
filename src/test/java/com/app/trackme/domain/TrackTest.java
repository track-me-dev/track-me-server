package com.app.trackme.domain;

import com.app.trackme.repository.TrackRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class TrackTest {

    @Autowired
    TrackRepository trackRepository;

    @Test
    @Transactional
    void test01() {

        List<Location> coordinates = List.of(
                new Location(37.512583, 126.960039, 0D)
        );
        Track track = Track.builder()
                .id(0L)
                .title("track01")
                .path(coordinates)
                .build();

        trackRepository.save(track);

        List<Track> tracks = trackRepository.findAll();
        Track track1 = tracks.get(0);

        assertThat(tracks.size()).isEqualTo(1);
        assertThat(track1.getPath().get(0).getLatitude()).isEqualTo(37.512583);
        assertThat(track1.getPath().get(0).getLongitude()).isEqualTo(126.960039);
    }

}