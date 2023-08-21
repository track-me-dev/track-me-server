package com.app.trackme.domain;

import com.app.trackme.dto.TrackDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Track {

    @Id
    @GeneratedValue
    @Column(name = "TRACK_ID")
    private Long id;

    private String title;

    @ElementCollection(fetch = FetchType.EAGER) // TODO : LAZY로 수정 예정
    @CollectionTable(
            name = "coordinate",
            joinColumns = @JoinColumn(name = "TRACK_ID")
    )
    private List<Coordinate> coordinates = new ArrayList<>();

    public static Track create(TrackDto dto) {
        return Track.builder()
                .title(dto.getTitle())
                .coordinates(dto.getCoordinates())
                .build();
    }
}
