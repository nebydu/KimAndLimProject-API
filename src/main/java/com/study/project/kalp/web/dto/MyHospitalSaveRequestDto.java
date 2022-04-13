package com.study.project.kalp.web.dto;

import com.study.project.kalp.domain.myHospital.MyHospital;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyHospitalSaveRequestDto {
    private String hospitalName;
    private String hospitalLocation;

    @Builder
    public MyHospitalSaveRequestDto(String hospitalName, String hospitalLocation) {
        this.hospitalName = hospitalName;
        this.hospitalLocation = hospitalLocation;
    }

    public MyHospital toEntity() {
        return MyHospital.builder()
                .hospitalName(hospitalName)
                .hospitalLocation(hospitalLocation)
                .build();
    }
}
