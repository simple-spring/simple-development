package com.spring.simple.development.demo.controller;

import com.jc.xauth.anno.authc.NoAuth;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author liko.wang
 * @Date 2020/4/10/010 19:14
 * @Description //TODO
 **/
@RestController
public class DemoController {
    @RequestMapping("/")
    public String home(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "hello world";
    }
    @NoAuth
    @RequestMapping("/index")
    public String index() {
        return "index";
    }
    @NoAuth
    @RequestMapping("/noPermission")
    public String noPermission() {
        return "noPermission";
    }
}