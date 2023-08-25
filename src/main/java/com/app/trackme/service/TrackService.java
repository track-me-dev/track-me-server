package com.app.trackme.service;

import com.app.trackme.domain.Track;
import com.app.trackme.dto.request.CreateTrackDTO;
import com.app.trackme.repository.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackService {

    private final TrackRecordService trackRecordService;
    private final TrackRepository trackRepository;

    @Transactional
    public Long createTrack(CreateTrackDTO dto) {
        Track track = Track.create(dto);
        Track trackEntity = trackRepository.save(track);
        trackRecordService.createTrackRecord(trackEntity, dto.getTrackRecord());
        return trackEntity.getId();
    }

    @Transactional(readOnly = true)
    public List<Track> findAllTracks() {
        return trackRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Track findTrack(Long trackId) {
        return trackRepository.findById(trackId)
                .orElseThrow();
    }
}
