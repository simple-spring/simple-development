package com.spring.simple.development.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("test")
@Api(value = "Test")
public class DemoSwaggerController {

    @RequestMapping("index")
    @ApiOperation(value = "进入首页面")
    public String index() {
        return "index";
    }

}
