package com.study.project.kalp.service.myHospital;

import com.study.project.kalp.domain.myHospital.MyHospitalRepository;
import com.study.project.kalp.web.dto.MyHospitalResponseDto;
import com.study.project.kalp.web.dto.MyHospitalSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class MyHospitalService {
    private final MyHospitalRepository myHospitalRepository;

    @Transactional(readOnly = true)
    public List<MyHospitalResponseDto> findAllDesc() {
        return myHospitalRepository.findAllDesc().stream()
                .map(MyHospitalResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public int save(MyHospitalSaveRequestDto requestDto) {
        return myHospitalRepository.save(requestDto.toEntity()).getId();
    }
}

