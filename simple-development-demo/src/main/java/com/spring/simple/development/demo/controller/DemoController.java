package com.spring.simple.development.demo.controller;

import com.acl.support.auth.web.authc.Account;
import com.acl.support.auth.web.util.AccountUtils;
import com.acl.xauth.anno.authc.NoAuth;
import com.alibaba.lava.privilege.PrivilegeInfo;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    PrivilegeInfo privilegeInfo;
    @RequestMapping("/")
    public String home(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "hello world";
    }
    @NoAuth
    @RequestMapping("/index")
    public String index(HttpServletRequest request,HttpServletResponse response) {
        String id = AccountUtils.getId();
        System.out.println(id);
        return "index";
    }
    @NoAuth
    @RequestMapping("/noPermission")
    public String noPermission() {
        return "noPermission";
    }
    @NoAuth
    @RequestMapping("/tologin")
    public String tologin(HttpServletRequest request,HttpServletResponse response) throws IOException {
        WebUtils.issueRedirect(request, response, "/login.do", null);
        return "noPermission";
    }
}