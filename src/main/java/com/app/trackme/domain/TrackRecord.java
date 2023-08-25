package com.app.trackme.domain;

import com.app.trackme.dto.request.CreateTrackRecordDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrackRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECORD_ID")
    private Long id;

    private Double time;

    @ManyToOne
    @JoinColumn(name = "TRACK_ID")
    private Track track;

    public static TrackRecord create(Track track, CreateTrackRecordDTO dto) {
        TrackRecord trackRecord = TrackRecord.builder()
                .time(dto.getTime())
                .build();
        trackRecord.setTrack(track);
        return trackRecord;
    }

    private void setTrack(Track track) {
        this.track = track;
        track.getRecords().add(this);
    }
}
