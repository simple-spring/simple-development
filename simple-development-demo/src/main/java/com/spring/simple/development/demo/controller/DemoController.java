package com.spring.simple.development.demo.controller;

import com.acl.support.auth.web.util.AccountUtils;
import com.acl.xauth.anno.authc.NoAuth;
import com.alibaba.lava.privilege.PrivilegeInfo;
import com.spring.simple.development.demo.service.TestDemoBo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author liko.wang
 * @Date 2020/4/10/010 19:14
 * @Description //TODO
 **/
@Controller
public class DemoController {
    private static final Logger logger = LogManager.getLogger(DemoController.class);


    @Autowired
    private TestDemoBo testDemoBo;
    @Autowired
    PrivilegeInfo privilegeInfo;

    //    @RequestMapping("/")
//    @ResponseBody
//    public String home(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        return "hello world";
//    }
    @NoAuth
    @ResponseBody
    @RequestMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        logger.info("123");
        logger.warn("456");
        logger.error("567");
        return "index";
    }

    @NoAuth
    @RequestMapping("/noPermission")
    public String noPermission() {
        return "noPermission";
    }

    @RequestMapping("/testPermission")
    public String testPermission(HttpServletRequest request, HttpServletResponse response) throws IOException {
        testDemoBo.getData("1");
        return "testPermission";
    }
}