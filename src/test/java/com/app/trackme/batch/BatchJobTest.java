package com.app.trackme.batch;

import com.app.trackme.domain.Coordinate;
import com.app.trackme.domain.Track;
import com.app.trackme.repository.TrackRepository;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BatchJobTest {

    @Autowired
    TrackRepository trackRepository;
    @Autowired
    Job job;
    @Autowired
    JobLauncher jobLauncher;

    @Test
    void job01() throws Exception {
        List<Coordinate> coordinates = List.of(
                new Coordinate(37.512583, 126.960039),
                new Coordinate(37.512070, 126.960971),
                new Coordinate(37.511660, 126.961716)
        );
        Track track = Track.builder()
                .id(0L)
                .title("track01")
                .path(coordinates)
                .build();
        trackRepository.save(track);

        jobLauncher.run(job, new JobParameters());

        Track findTrack = trackRepository.findAll().get(0);
        System.out.println(findTrack.getLowestAltitude());
        System.out.println(findTrack.getHighestAltitude());
        System.out.println(findTrack.getAverageSlope());
    }
}
