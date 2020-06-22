package com.spring.simple.development.core.component.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description:
 * @author: luke
 * @time: 2020/6/11 0011 9:24
 */
@Controller
public class Home {
    @RequestMapping("/")
    public String home() {
        return "forward:/simpleDoc/index.html";
    }
}
