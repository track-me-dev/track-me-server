package com.app.trackme.controller;

import com.app.trackme.dto.TrackDto;
import com.app.trackme.service.TrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tracks")
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;

    @PostMapping
    public ResponseEntity<TrackDto> createTrack(@RequestBody TrackDto trackDto) {
        Long trackId = trackService.saveTrack(trackDto);
        return ResponseEntity.ok(TrackDto.toDto(trackService.findTrack(trackId)));
    }

    @GetMapping
    public ResponseEntity<List<TrackDto>> retrieveAllTracks() {
        List<TrackDto> trackDtos = trackService.findAllTracks().stream()
                .map(TrackDto::toDto)
                .toList();
        return ResponseEntity.ok(trackDtos);
    }

    @GetMapping("/{trackId}")
    public ResponseEntity<TrackDto> retrieveTrack(@PathVariable Long trackId) {
        return ResponseEntity.ok(TrackDto.toDto(trackService.findTrack(trackId)));
    }
}
