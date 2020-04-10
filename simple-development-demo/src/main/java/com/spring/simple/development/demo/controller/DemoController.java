package com.spring.simple.development.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liko.wang
 * @Date 2020/4/10/010 19:14
 * @Description //TODO
 **/
@RestController
public class DemoController {
    @RequestMapping("/home")
    public String home() {
        return "hello world";
    }
    @RequestMapping("/index")
    public String index() {
        return "index";
    }
    @RequestMapping("/noPermission")
    public String noPermission() {
        return "noPermission";
    }
}