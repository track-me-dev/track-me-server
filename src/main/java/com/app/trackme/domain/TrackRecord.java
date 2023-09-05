package com.app.trackme.domain;

import com.app.trackme.dto.request.CreateTrackRecordDTO;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

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

    @ElementCollection
    @CollectionTable(
            name = "LOCATION_FOR_TRACK_RECORD",
            joinColumns = @JoinColumn(name = "TRACK_RECORD_ID")
    )
    private List<Location> path;

    @ManyToOne
    @JoinColumn(name = "TRACK_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Track track;

    public static TrackRecord create(Track track, CreateTrackRecordDTO dto) {
        TrackRecord trackRecord = TrackRecord.builder()
                .time(dto.getTime())
                .distance(dto.getDistance())
                .path(dto.getPath())
                .build();
        trackRecord.setTrack(track);
        return trackRecord;
    }

    private void setTrack(Track track) {
        this.track = track;
        track.getRecords().add(this);
    }
}
