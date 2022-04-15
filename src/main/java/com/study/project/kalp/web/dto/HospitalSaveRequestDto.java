package com.study.project.kalp.web.dto;

import com.study.project.kalp.domain.myHospital.Hospital;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HospitalSaveRequestDto {
    private String city;
    private String district;
    private String hospitalName;
    private String hospitalType;
    private String telNo;
    private String adtFrDd;
    private String spclAdmTypeCd;

    @Builder
    public HospitalSaveRequestDto(String city, String district, String hospitalName, String hospitalType, String telNo, String adtFrDd, String spclAdmTypeCd) {
        this.city = city;
        this.district = district;
        this.hospitalName = hospitalName;
        this.hospitalType = hospitalType;
        this.telNo = telNo;
        this.adtFrDd = adtFrDd;
        this.spclAdmTypeCd = spclAdmTypeCd;
    }

    public Hospital toEntity() {
        return Hospital.builder()
                .city(city)
                .district(district)
                .hospitalName(hospitalName)
                .hospitalType(hospitalType)
                .telNo(telNo)
                .adtFrDd(adtFrDd)
                .spclAdmTypeCd(spclAdmTypeCd)
                .build();
    }
}
