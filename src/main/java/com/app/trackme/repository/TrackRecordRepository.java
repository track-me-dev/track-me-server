package com.app.trackme.repository;

import com.app.trackme.domain.TrackRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrackRecordRepository extends JpaRepository<TrackRecord, Long> {

    Slice<TrackRecord> findAllByTrackIdOrderByTime(Long trackId, Pageable pageable);

    @Query("select r from TrackRecord r where r.track.id = :trackId order by r.time asc")
    List<TrackRecord> findRecordsOrderByTime(@Param("trackId") Long trackId);
}
