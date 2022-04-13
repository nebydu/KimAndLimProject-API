package com.study.project.kalp.web.dto;

import com.study.project.kalp.domain.myHospital.MyHospital;
import lombok.Getter;

@Getter
public class MyHospitalResponseDto {
    private int id;
    private String hospitalName;
    private String hospitalLocation;

    public MyHospitalResponseDto(MyHospital entity) {
        this.id = entity.getId();
        this.hospitalName = entity.getHospitalName();
        this.hospitalLocation = entity.getHospitalLocation();
    }
}
