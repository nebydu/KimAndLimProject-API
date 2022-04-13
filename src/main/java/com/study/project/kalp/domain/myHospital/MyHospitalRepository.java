package com.study.project.kalp.domain.myHospital;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyHospitalRepository extends JpaRepository<MyHospital, Integer> {

    @Query("SELECT h FROM MyHospital h ORDER BY h.id DESC")
    List<MyHospital> findAllDesc();

}
