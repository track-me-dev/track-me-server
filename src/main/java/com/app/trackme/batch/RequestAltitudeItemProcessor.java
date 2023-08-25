package com.app.trackme.batch;

import com.app.trackme.domain.Track;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class RequestAltitudeItemProcessor implements ItemProcessor<Track, Track> {

    private final String API_KEY = "AIzaSyDqXtVuWDie8xhCudLEuB83_u6osXD-twQ";
    private final String API_URL = "https://maps.googleapis.com/maps/api/elevation/json";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Track process(Track track) {
        // TODO: locations 파라미터 (위도,경도) 512개까지 제한
        String locationsParam = track.getPath().stream()
                .map(coord -> coord.getLatitude() + "," + coord.getLongitude())
                .collect(Collectors.joining("|"));
        String requestUrl = UriComponentsBuilder.fromUriString(API_URL)
                .queryParam("key", API_KEY)
                .queryParam("locations", locationsParam)
                .build(false)
                .toUriString();
        ElevationResponse response = restTemplate.getForObject(requestUrl, ElevationResponse.class);
        List<ElevationResult> results = Objects.requireNonNull(response).getResults();

        track.setElevationResult(results);

        return track;
    }
}
