package com.app.trackme.init;

import com.app.trackme.domain.Location;
import com.app.trackme.dto.request.CreateTrackDTO;
import com.app.trackme.dto.request.CreateTrackRecordDTO;
import com.app.trackme.service.TrackService;
import com.app.trackme.utils.GeoUtils;
import io.jenetics.jpx.GPX;
import io.jenetics.jpx.WayPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class TestDataInitializer {

    private final ObjectProvider<OrderedPostConstruct> initializers;

    @EventListener(ApplicationReadyEvent.class)
    public void initEntryPoint() {
        initializers.orderedStream()
                .forEach(OrderedPostConstruct::init);
    }

    @Order(1)
    @Component
    @RequiredArgsConstructor
    static class TrackInit implements OrderedPostConstruct {

        static String[] USERNAMES = {"끝까지간다", "10년째백수", "이클립스", "달려라 왕바우", "미친 허벅지", "사이클은 장비빨",
                "드가자잇", "고독한 하루", "뚜르드루뜨", "회전드릴킥"};

        private final TrackService trackService;
        private final Job job;
        private final JobLauncher jobLauncher;

        @Override
        public void init() {
            Random random = new Random();
            try (Stream<Path> paths = Files.walk(Paths.get("/Users/jinhyeon/KJH/dev/trackme-server/track-me/src/main/resources/initdata/gpx"))) {
                paths
                        .filter(Files::isRegularFile)
                        .forEach(p -> {
                            try {
                                GPX gpx = GPX.read(p);
                                List<WayPoint> wayPoints = gpx.getWayPoints();
                                List<Location> path = wayPoints.stream()
                                        .map(w -> new Location(w.getLatitude().doubleValue(), w.getLongitude().doubleValue()))
                                        .toList();
                                double distance = GeoUtils.calculateDistance(path.get(0), path.get(path.size() - 1));
                                long startTime = wayPoints.get(0).getTime().get().getEpochSecond();
                                long endTime = wayPoints.get(wayPoints.size() - 1).getTime().get().getEpochSecond();
                                double elapsedTime = (double) (endTime - startTime) * 1000;
                                String username = USERNAMES[random.nextInt(USERNAMES.length)];

                                CreateTrackRecordDTO trackRecordDTO = CreateTrackRecordDTO.builder()
                                        .path(path)
                                        .distance(distance)
                                        .time(elapsedTime)
                                        .username(username)
                                        .build();

                                CreateTrackDTO trackDTO = CreateTrackDTO.builder()
                                        .title(p.getFileName().toString())
                                        .path(path)
                                        .distance(distance)
                                        .trackRecord(trackRecordDTO)
                                        .createdBy(username)
                                        .build();

                                trackService.createTrack(trackDTO);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                jobLauncher.run(job, new JobParameters());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
