package com.study.project.kalp.domain.myHospital;

import com.study.project.kalp.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class MyHospital extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String hospitalName;

    @Column(nullable = false)
    private String hospitalLocation;

    @Builder
    public MyHospital(String hospitalName, String hospitalLocation) {
        this.hospitalName = hospitalName;
        this.hospitalLocation = hospitalLocation;
    }
}
