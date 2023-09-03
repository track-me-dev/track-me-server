package com.app.trackme.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

/**
 * Track 정보에서 주행 경로의 각 포인트를 포현하기 위한 데이터
 */
@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    private Double latitude;
    private Double longitude;
    private Double elapsedTime; // Track.path 에서 first location 을 elapsedTime = 0 으로 설정
}
