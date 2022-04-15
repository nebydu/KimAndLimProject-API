package com.study.project.kalp.web;

import com.study.project.kalp.service.myHospital.HospitalService;
import com.study.project.kalp.web.dto.HospitalResponseDto;
import com.study.project.kalp.web.dto.HospitalSaveRequestDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class HospitalController {

    private final Logger logger = LoggerFactory.getLogger(HospitalController.class);

    private final HospitalService hospitalService;

    @Value("${kalp.api.url}")
    private String apiURL;

    @Value("${kalp.api.key}")
    private String apiKey;

    @ApiOperation(value= "", notes="병원정보모두보기", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200 , message = "Success"),
            @ApiResponse(code = 401 , message = "Not authorize"),
            @ApiResponse(code = 404 , message = "Not found")
    })
    @GetMapping("/api/v1/myHospital/findAllDesc")
    public List<HospitalResponseDto> findAllDesc(){
        return hospitalService.findAllDesc();
    }

    @PostMapping("/api/v1/myHospital/save")
    public int save(@RequestBody HospitalSaveRequestDto requestDto) {
        return hospitalService.save(requestDto);
    }

    @ApiOperation(value= "", notes="병원정보가져오기", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200 , message = "Success"),
            @ApiResponse(code = 401 , message = "Not authorize"),
            @ApiResponse(code = 404 , message = "Not found")
    })
    @GetMapping("/api/v1/publicHospitalList")
    public void getPublicHospitalList() throws Exception {
        //공공데이터 오픈 API 호출한다
        String data = this.getMainHospitalData();
    }

    private String getMainHospitalData() throws Exception {
        logger.info("apiURL="+apiURL);
        logger.info("apiKey="+apiKey);
        StringBuilder urlBuilder = new StringBuilder(apiURL); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" +apiKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("spclAdmTyCd","UTF-8") + "=" + URLEncoder.encode("A0", "UTF-8")); /*A0: 국민안심병원/97: 코로나검사 실시기관/99: 코로나 선별진료소 운영기관*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        logger.info("Response code: " + conn.getResponseCode());
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
        logger.info(sb.toString());
        //getResultDto(sb.toString());
        return sb.toString();
    }

    private void getResultDto(String data) throws Exception {
        List<HospitalSaveRequestDto> saveData = new ArrayList<>();

        InputSource is = new InputSource(new StringReader(data));

        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);

        document.getDocumentElement().normalize();

        NodeList nodeList = document.getElementsByTagName("item");

        for(int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                HospitalSaveRequestDto hospitalSaveRequestDto = new HospitalSaveRequestDto();
                hospitalSaveRequestDto.setCity(getTagValue("sidoNm", element));
                hospitalSaveRequestDto.setDistrict(getTagValue("sgguNm", element));
                hospitalSaveRequestDto.setHospitalName(getTagValue("yadmNm", element));
                hospitalSaveRequestDto.setHospitalType(getTagValue("hospTyTpCd", element));
                hospitalSaveRequestDto.setTelNo(getTagValue("telno", element));
                hospitalSaveRequestDto.setAdtFrDd(getTagValue("adtFrDd", element));
                hospitalSaveRequestDto.setSpclAdmTypeCd(getTagValue("spclAdmTypeCd", element));

                logger.info("병원이름 = "+hospitalSaveRequestDto.getHospitalName());
                logger.info(hospitalSaveRequestDto.toString());
                //hospitalService.save(hospitalSaveRequestDto);
            }
        }
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node)nList.item(0);

        if(nValue == null) {
            return null;
        }
        return nValue.getNodeValue();
    }
}
