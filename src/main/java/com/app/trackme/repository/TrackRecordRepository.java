package com.app.trackme.repository;

import com.app.trackme.domain.TrackRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrackRecordRepository extends JpaRepository<TrackRecord, Long> {

    List<TrackRecord> findAllByTrack(Long trackId);
}
