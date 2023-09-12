package com.app.trackme.controller;

import com.app.trackme.domain.Track;
import com.app.trackme.domain.TrackRecord;
import com.app.trackme.dto.response.TrackViewResponseDTO;
import com.app.trackme.dto.response.SimpleTrackResponseDTO;
import com.app.trackme.dto.response.TrackRecordResponseDTO;
import com.app.trackme.dto.request.CreateTrackDTO;
import com.app.trackme.dto.request.CreateTrackRecordDTO;
import com.app.trackme.service.TrackRecordService;
import com.app.trackme.service.TrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<SimpleTrackResponseDTO> createTrack(@RequestBody CreateTrackDTO dto) {
        Long trackId = trackService.createTrack(dto);
        try {
            jobLauncher.run(job, new JobParameters());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(SimpleTrackResponseDTO.toDto(trackService.findTrack(trackId)));
    }

    @GetMapping
    public ResponseEntity<List<SimpleTrackResponseDTO>> retrieveAllTracks() {
        List<SimpleTrackResponseDTO> trackDTOs = trackService.findAllTracks().stream()
                .map(SimpleTrackResponseDTO::toDto)
                .toList();
        return ResponseEntity.ok(trackDTOs);
    }

    @GetMapping("/{trackId}")
    public ResponseEntity<TrackViewResponseDTO> retrieveTrack(@PathVariable Long trackId) {
        Track track = trackService.findTrack(trackId);
        TrackRecord rank1stRecord = trackRecordService.findRank1stRecord(track.getId());
        return ResponseEntity.ok(TrackViewResponseDTO.toDto(track, rank1stRecord));
    }

    @GetMapping("/{trackId}/records")
    public ResponseEntity<Slice<TrackRecordResponseDTO>> retrieveAllRecords(@PathVariable Long trackId,
                                                                            @PageableDefault(size = 5) Pageable pageable) {
        Slice<TrackRecordResponseDTO> trackRecordDTOs = trackRecordService.findTrackRecords(trackId, pageable)
                .map(TrackRecordResponseDTO::toDto);
        return ResponseEntity.ok(trackRecordDTOs);
    }

    @PostMapping("/{trackId}/records")
    public void createRecordOfTrack(@PathVariable Long trackId, @RequestBody CreateTrackRecordDTO dto) {
        trackService.createTrackRecord(trackId, dto);
    }
}
