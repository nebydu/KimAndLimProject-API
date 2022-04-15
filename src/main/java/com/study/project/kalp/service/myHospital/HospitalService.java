package com.study.project.kalp.service.myHospital;

import com.study.project.kalp.domain.myHospital.HospitalRepository;
import com.study.project.kalp.web.dto.HospitalResponseDto;
import com.study.project.kalp.web.dto.HospitalSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class HospitalService {
    private final HospitalRepository hospitalRepository;

    @Transactional(readOnly = true)
    public List<HospitalResponseDto> findAllDesc() {
        return hospitalRepository.findAllDesc().stream()
                .map(HospitalResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public int save(HospitalSaveRequestDto requestDto) {
        return hospitalRepository.save(requestDto.toEntity()).getId();
    }
}

