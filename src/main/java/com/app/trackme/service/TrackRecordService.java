package com.app.trackme.service;

import com.app.trackme.domain.Track;
import com.app.trackme.domain.TrackRecord;
import com.app.trackme.dto.request.CreateTrackRecordDTO;
import com.app.trackme.repository.TrackRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackRecordService {

    private final TrackRecordRepository trackRecordRepository;

    @Transactional
    public Long createTrackRecord(Track track, CreateTrackRecordDTO dto) {
        TrackRecord trackRecord = TrackRecord.create(track, dto);
        TrackRecord entity = trackRecordRepository.save(trackRecord);
        return entity.getId();
    }

    @Transactional(readOnly = true)
    public List<TrackRecord> findAllRecords() {
        return trackRecordRepository.findAll();
    }

    @Transactional(readOnly = true)
    public TrackRecord findTrackRecord(Long trackRecordId) {
        return trackRecordRepository.findById(trackRecordId)
                .orElseThrow();
    }

    @Transactional(readOnly = true)
    public Slice<TrackRecord> findTrackRecords(Long trackId, Pageable pageable) {
        return trackRecordRepository.findAllByTrackIdOrderByTime(trackId, pageable);
    }

    @Transactional(readOnly = true)
    public TrackRecord findRank1stRecord(Long trackId) {
        return trackRecordRepository.findRecordsOrderByTime(trackId).get(0);
    }
}
