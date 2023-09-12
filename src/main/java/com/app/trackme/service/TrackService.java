package com.app.trackme.service;

import com.app.trackme.domain.Track;
import com.app.trackme.domain.TrackRecord;
import com.app.trackme.dto.request.CreateTrackDTO;
import com.app.trackme.dto.request.CreateTrackRecordDTO;
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
        track = trackRepository.save(track);
        Long trackRecordId = trackRecordService.createTrackRecord(track, dto.getTrackRecord());
        // 현재 기록은 트랙의 첫 번째 기록이므로 랭킹 1위의 기록으로 저장
        track.updateRank1st(trackRecordId);
        return track.getId();
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

    @Transactional
    public void createTrackRecord(Long trackId, CreateTrackRecordDTO dto) {
        Track track = findTrack(trackId);
        Long trackRecordId = trackRecordService.createTrackRecord(track, dto);

        TrackRecord currentRank1st = trackRecordService.findTrackRecord(track.getRank1stId());
        Long rank1stRecordId;
        if (currentRank1st.getTime() > dto.getTime()) {
            rank1stRecordId = trackRecordId;
        } else {
            TrackRecord rank1stRecord = trackRecordService.findRank1stRecord(trackId);
            rank1stRecordId = rank1stRecord.getId();
        }
        track.updateRank1st(rank1stRecordId);
    }
}
