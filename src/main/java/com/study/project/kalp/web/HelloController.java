package com.study.project.kalp.web;

import com.study.project.kalp.web.dto.HelloResponseDto;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @ApiOperation(value= "", notes="안녕", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200 , message = "Success"),
            @ApiResponse(code = 401 , message = "Not authorize"),
            @ApiResponse(code = 404 , message = "Not found")
    })
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @ApiOperation(value= "", notes="안녕누구야", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200 , message = "Success"),
            @ApiResponse(code = 401 , message = "Not authorize"),
            @ApiResponse(code = 404 , message = "Not found")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "이름", required =true, dataType = "string", defaultValue = "KALP")
    })
    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name) {
        return new HelloResponseDto(name);
    }
}
