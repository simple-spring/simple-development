//package com.spring.simple.development.core.component.mvc.controller;
//
//import com.spring.simple.development.core.baseconfig.isapiservice.ServiceInvoke;
//import com.spring.simple.development.core.component.mvc.req.ReqBody;
//import com.spring.simple.development.core.component.mvc.req.RpcRequest;
//import com.spring.simple.development.core.component.mvc.res.ResBody;
//import com.spring.simple.development.support.utils.DateUtils;
//import com.spring.simple.development.support.utils.HttpRequestUtil;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//import com.alibaba.fastjson.JSONObject;
//import io.swagger.annotations.*;
//import com.spring.simple.development.core.annotation.base.NoLogin;
//
//import javax.servlet.http.HttpServletRequest;import java.util.HashMap;
//import java.util.Map;
//
//import com.spring.simple.development.demo.vo.DemoVo;
//
///**
// * swagger接口
// *
// * @author liko
// */
//@RestController
//@Api(tags = "用户相关")
//public class DemoBoController {
//    private static final Logger logger = LogManager.getLogger(DemoBoController.class);
//
//    @Autowired
//    private ServiceInvoke serviceInvoke;
//
//
//    @NoLogin
//    @RequestMapping(value = "/data/api/v1/DemoBo/getData", method = RequestMethod.POST)
//    @ApiOperation(value="查询", notes="查询一亿个订单")
//    public ResBody getData(HttpServletRequest request, @ApiParam(name = "demoVo",value="用户vo")@RequestBody DemoVo demoVo) throws Throwable {
//        logger.info("requestJson:" + JSONObject.toJSONString(demoVo) + " date:" + DateUtils.getCurrentTime());
//        RpcRequest rpcRequest = new RpcRequest();
//        rpcRequest.setServiceName("DemoBo");
//        rpcRequest.setMethodName("getData");
//        ReqBody reqBody = new ReqBody();
//        Map<String,Object> params= new HashMap<>();
//        params.put("demoVo",demoVo);
//        reqBody.setParamsMap(params);
//        rpcRequest.setReqBody(reqBody);
//        return serviceInvoke.invokeService(rpcRequest);
//    }
//
//}