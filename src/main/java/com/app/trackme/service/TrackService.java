package com.app.trackme.service;

import com.app.trackme.domain.Track;
import com.app.trackme.dto.TrackDto;
import com.app.trackme.repository.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackService {

    private final TrackRepository trackRepository;

    @Transactional
    public Long saveTrack(TrackDto trackDto) {
        Track track = Track.create(trackDto);
        Track entity = trackRepository.save(track);
        return entity.getId();
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
