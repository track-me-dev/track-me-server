package com.app.trackme.controller;

import com.app.trackme.dto.TrackResponseDTO;
import com.app.trackme.dto.TrackRecordDto;
import com.app.trackme.dto.request.CreateTrackDTO;
import com.app.trackme.dto.request.CreateTrackRecordDTO;
import com.app.trackme.service.TrackRecordService;
import com.app.trackme.service.TrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tracks")
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;
    private final TrackRecordService trackRecordService;

    // TODO: test용 삭제
    private final Job job;
    private final JobLauncher jobLauncher;

    @PostMapping
    public ResponseEntity<TrackResponseDTO> createTrack(@RequestBody CreateTrackDTO dto) {
        Long trackId = trackService.createTrack(dto);
        try {
            jobLauncher.run(job, new JobParameters());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(TrackResponseDTO.toDto(trackService.findTrack(trackId)));
    }

    @GetMapping
    public ResponseEntity<List<TrackResponseDTO>> retrieveAllTracks() {
        List<TrackResponseDTO> trackDTOs = trackService.findAllTracks().stream()
                .map(TrackResponseDTO::toDto)
                .toList();
        return ResponseEntity.ok(trackDTOs);
    }

    @GetMapping("/{trackId}")
    public ResponseEntity<TrackResponseDTO> retrieveTrack(@PathVariable Long trackId) {
        return ResponseEntity.ok(TrackResponseDTO.toDto(trackService.findTrack(trackId)));
    }

    @GetMapping("/{trackId}/records")
    public ResponseEntity<List<TrackRecordDto>> retrieveAllRecords(@PathVariable Long trackId) {
        List<TrackRecordDto> trackRecordDTOs = trackRecordService.findRecordsByTrack(trackId).stream()
                .map(TrackRecordDto::toDto)
                .toList();
        return ResponseEntity.ok(trackRecordDTOs);
    }

    @PostMapping("/{trackId}/records")
    public void createRecordOfTrack(@PathVariable Long trackId, @RequestBody CreateTrackRecordDTO dto) {
        trackService.createTrackRecord(trackId, dto);
    }
}
