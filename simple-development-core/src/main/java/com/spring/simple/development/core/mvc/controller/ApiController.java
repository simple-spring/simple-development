package com.spring.simple.development.core.mvc.controller;

import com.alibaba.fastjson.JSONObject;

import com.spring.simple.development.core.annotation.NoLogin;
import com.spring.simple.development.core.constant.VersionConstant;
import com.spring.simple.development.core.handler.ServiceInvoke;
import com.spring.simple.development.core.mvc.req.ReqBody;
import com.spring.simple.development.core.mvc.req.RpcRequest;
import com.spring.simple.development.core.mvc.res.ResBody;
import com.spring.simple.development.support.utils.DateUtils;
import com.spring.simple.development.support.utils.HttpRequestUtil;
import com.spring.simple.development.support.GlobalException;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import static com.spring.simple.development.support.ResponseCode.RES_PARAM_INVALID;
import static com.spring.simple.development.support.ResponseCode.RES_PARAM_IS_EMPTY;


/**
 * api统一入口
 *
 * @author liko
 */
@Controller
@RequestMapping("/data")
public class ApiController {
    private static final Logger logger = LogManager.getLogger(ApiController.class);
    private static final Logger apiUserLogger = LogManager.getLogger("userLogger");

    @Autowired
    private ServiceInvoke serviceInvoke;
    /**
     * 登录后接口服务
     *
     * @param request
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = {"/api/{version}/{serviceName}/{methodName}", "/api", "/api/{version}"})
    @ResponseBody
    public ResBody api(HttpServletRequest request, @PathVariable(name = "version", required = false) String version, @PathVariable(name = "serviceName", required = false) String serviceName, @PathVariable(name = "methodName", required = false) String methodName) throws Throwable {

        if (StringUtils.isEmpty(version)) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "版本号为空");
        }
        if (version.equals(VersionConstant.APP_VERSION_1) == false) {
            throw new GlobalException(RES_PARAM_INVALID, "版本号无效");
        }
        String paramJson = HttpRequestUtil.readStringFromRequestBody(request);

        logger.info("requestJson:" + paramJson + " date:" + DateUtils.getCurrentTime());
        // 解析
        if (StringUtils.isEmpty(paramJson)) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "服务器未收到信息");
        }
        RpcRequest rpcRequest = new RpcRequest();
        try {
            if (!StringUtils.isEmpty(serviceName) && !StringUtils.isEmpty(methodName)) {
                ReqBody reqBody = JSONObject.parseObject(paramJson, ReqBody.class);
                rpcRequest.setServiceName(serviceName);
                rpcRequest.setMethodName(methodName);
                rpcRequest.setReqBody(reqBody);
            } else {
                rpcRequest = JSONObject.parseObject(paramJson, RpcRequest.class);
            }
        } catch (Exception ex) {
            logger.error(" date:" + DateUtils.getCurrentTime(), ex);
            throw new GlobalException(RES_PARAM_INVALID, "格式错误");
        }
        // invoke
        return serviceInvoke.invokeService(rpcRequest, request);

    }

    /**
     * 非登录接口服务
     *
     * @param request
     * @return
     * @throws Throwable
     */
    @NoLogin
    @RequestMapping(value = {"/config/{version}/{serviceName}/{methodName}", "/config", "/config/{version}"})
    @ResponseBody
    public ResBody config(HttpServletRequest request, @PathVariable(name = "version", required = false) String version, @PathVariable(name = "serviceName", required = false) String serviceName, @PathVariable(name = "methodName", required = false) String methodName) throws Throwable {
        if (StringUtils.isEmpty(version)) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "版本号为空");
        }
        if (version.equals(VersionConstant.APP_VERSION_1) == false) {
            throw new GlobalException(RES_PARAM_INVALID, "版本号无效");
        }
        // 读取参数
        String paramJson = HttpRequestUtil.readStringFromRequestBody(request);
        logger.info("requestJson:" + paramJson + " date:" + DateUtils.getCurrentTime());
        // 解析
        if (StringUtils.isEmpty(paramJson)) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "服务器未收到信息");
        }
        RpcRequest rpcRequest = new RpcRequest();
        try {
            if (!StringUtils.isEmpty(serviceName) && !StringUtils.isEmpty(methodName)) {
                ReqBody reqBody = JSONObject.parseObject(paramJson, ReqBody.class);
                rpcRequest.setServiceName(serviceName);
                rpcRequest.setMethodName(methodName);
                rpcRequest.setReqBody(reqBody);
            } else {
                rpcRequest = JSONObject.parseObject(paramJson, RpcRequest.class);
            }
        } catch (Exception ex) {
            logger.error(" date:" + DateUtils.getCurrentTime(), ex);
            throw new GlobalException(RES_PARAM_INVALID, "格式错误");
        }
        // invoke
        return serviceInvoke.invokeConfigService(rpcRequest, request);

    }
}
