package com.study.project.kalp.web;

import com.study.project.kalp.service.myHospital.MyHospitalService;
import com.study.project.kalp.web.dto.MyHospitalResponseDto;
import com.study.project.kalp.web.dto.MyHospitalSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MyHospitalController {

    private final MyHospitalService myHospitalService;

    private final Logger logger = LoggerFactory.getLogger(MyHospitalController.class);

    @Value("${kalp.api.url}")
    private String apiURL;

    @Value("${kalp.api.key}")
    private String apiKey;

    @GetMapping("/api/v1/myHospital/findAllDesc")
    public List<MyHospitalResponseDto> findAllDesc(){
        return myHospitalService.findAllDesc();
    }

    @PostMapping("/api/v1/myHospital/save")
    public int save(@RequestBody MyHospitalSaveRequestDto requestDto) {
        return myHospitalService.save(requestDto);
    }

    @GetMapping("/api/v1/publicHospitalList")
    public void getPublicHospitalList() throws IOException {
        //공공데이터 오픈 API 호출한다
        String data = this.getMainHospitalData();
    }

    private String getMainHospitalData() throws IOException{
        logger.debug("apiURL="+apiURL);
        logger.debug("apiKey="+apiKey);
        StringBuilder urlBuilder = new StringBuilder(apiURL); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" +apiKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("spclAdmTyCd","UTF-8") + "=" + URLEncoder.encode("A0", "UTF-8")); /*A0: 국민안심병원/97: 코로나검사 실시기관/99: 코로나 선별진료소 운영기관*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        logger.debug("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        logger.debug(sb.toString());
        return sb.toString();
    }
}
