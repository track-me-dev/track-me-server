package com.app.trackme.domain;

import com.app.trackme.dto.request.CreateTrackRecordDTO;
import com.app.trackme.utils.PathUtils;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrackRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRACK_RECORD_ID")
    private Long id;
    private Double time;
    private Double distance;
    private String username;
    @Lob private String encodedPath;

    @ManyToOne
    @JoinColumn(name = "TRACK_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Track track;

    public static TrackRecord create(Track track, CreateTrackRecordDTO dto) {
        TrackRecord trackRecord = TrackRecord.builder()
                .time(dto.getTime())
                .distance(dto.getDistance())
                .username(dto.getUsername())
                .encodedPath(PathUtils.encode(dto.getPath()))
                .build();
        trackRecord.setTrack(track);
        return trackRecord;
    }

    private void setTrack(Track track) {
        this.track = track;
        track.getRecords().add(this);
    }
}
