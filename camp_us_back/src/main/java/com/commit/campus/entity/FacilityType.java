package com.commit.campus.entity;

import lombok.Getter;
import jakarta.persistence.*;

@Entity
@Getter
public class FacilityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facsTypeId;  // 시설 유형 ID

    private String facilityName;  // 시설 이름
    private int capacity;  // 수용 인원
}
