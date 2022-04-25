package com.study.project.kalp.web.dto;

import com.study.project.kalp.domain.myHospital.Hospital;
import lombok.Getter;

@Getter
public class HospitalResponseDto {
    private int id;
    private String city;
    private String district;
    private String hospitalName;
    private String telNo;
    private String adtFrDd;
    private String spclAdmTypeCd;

    public HospitalResponseDto(Hospital entity) {
        this.id = entity.getId();
        this.city = entity.getCity();
        this.district = entity.getDistrict();
        this.hospitalName = entity.getHospitalName();
        this.telNo = entity.getTelNo();
        this.adtFrDd = entity.getAdtFrDd();
        this.spclAdmTypeCd = entity.getSpclAdmTypeCd();
    }
}
