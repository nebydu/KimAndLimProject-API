package com.study.project.kalp.domain.myHospital;

import com.study.project.kalp.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Hospital extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String hospitalName;

    @Column
    private String hospitalType;

    @Column
    private String telNo;

    @Column
    private String adtFrDd;

    @Column
    private String spclAdmTypeCd;

    @Builder
    public Hospital(String city, String district, String hospitalName, String hospitalType, String telNo, String adtFrDd, String spclAdmTypeCd) {
        this.city = city;
        this.district = district;
        this.hospitalName = hospitalName;
        this.hospitalType = hospitalType;
        this.telNo = telNo;
        this.adtFrDd = adtFrDd;
        this.spclAdmTypeCd = spclAdmTypeCd;
    }
}
