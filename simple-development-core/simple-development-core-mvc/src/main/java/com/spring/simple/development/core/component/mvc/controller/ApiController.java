package com.spring.simple.development.core.component.mvc.controller;

import com.alibaba.fastjson.JSONObject;
import com.spring.simple.development.core.annotation.base.NoLogin;
import com.spring.simple.development.core.baseconfig.context.SimpleApplication;
import com.spring.simple.development.core.baseconfig.isapiservice.MethodParams;
import com.spring.simple.development.core.baseconfig.user.SimpleSessionProfile;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import com.spring.simple.development.core.component.mvc.req.ReqBody;
import com.spring.simple.development.core.component.mvc.req.RpcRequest;
import com.spring.simple.development.support.constant.VersionConstant;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.exception.GlobalResponseCode;
import com.spring.simple.development.support.utils.DateUtils;
import com.spring.simple.development.support.utils.HttpRequestUtil;
import com.spring.simple.development.core.baseconfig.isapiservice.ServerFactory;
import com.spring.simple.development.core.baseconfig.isapiservice.ServiceInvoke;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static com.spring.simple.development.support.exception.GlobalResponseCode.SERVICE_NOT_EXIST;
import static com.spring.simple.development.support.exception.ResponseCode.RES_PARAM_INVALID;
import static com.spring.simple.development.support.exception.ResponseCode.RES_PARAM_IS_EMPTY;


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
    @RequestMapping(value = {"/api/{version}/{serviceName}/{methodName}", "/api", "/api/{version}"}, method = RequestMethod.POST)
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
        if (!StringUtils.isEmpty(serviceName) && !StringUtils.isEmpty(methodName)) {
            rpcRequest.setServiceName(serviceName);
            rpcRequest.setMethodName(methodName);
            ReqBody reqBody = new ReqBody();
            MethodParams methodParams = ServerFactory.serviceMethodMap.get(serviceName + "-" + methodName);
            Object o = JSONObject.parseObject(paramJson, methodParams.getMethodClass()[0]);
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put(methodParams.getKey()[0], o);
            reqBody.setParamsMap(paramsMap);
            rpcRequest.setReqBody(reqBody);
        } else {
            // 旧版本调用
            rpcRequest = JSONObject.parseObject(paramJson, RpcRequest.class);
            return serviceInvoke.invokeServiceOld(rpcRequest);
        }
        // invoke
        return serviceInvoke.invokeService(rpcRequest);
    }

    /**
     * 非登录接口服务
     *
     * @param request
     * @return
     * @throws Throwable
     */
    @NoLogin
    @RequestMapping(value = {"/config/{version}/{serviceName}/{methodName}", "/config", "/config/{version}"}, method = RequestMethod.POST)
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
        if (!StringUtils.isEmpty(serviceName) && !StringUtils.isEmpty(methodName)) {
            rpcRequest.setServiceName(serviceName);
            rpcRequest.setMethodName(methodName);
            ReqBody reqBody = new ReqBody();
            MethodParams methodParams = ServerFactory.serviceMethodMap.get(serviceName + "-" + methodName);
            Object o = JSONObject.parseObject(paramJson, methodParams.getMethodClass()[0]);
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put(methodParams.getKey()[0], o);
            reqBody.setParamsMap(paramsMap);
            rpcRequest.setReqBody(reqBody);
        } else {
            // 旧版本调用
            rpcRequest = JSONObject.parseObject(paramJson, RpcRequest.class);
            return serviceInvoke.invokeConfigServiceOld(rpcRequest);
        }
        // invoke
        return serviceInvoke.invokeConfigService(rpcRequest);
    }

    /**
     * 用户登录接口
     *
     * @param request
     * @return
     * @throws Throwable
     */
    @NoLogin
    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    @ResponseBody
    public ResBody privilegeInfoLogin(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        // 获取已实现的接口
        if (SimpleApplication.isExistBean("simpleSessionProfile") == false) {
            // 未实现接口
            throw new GlobalException(SERVICE_NOT_EXIST);
        }
        SimpleSessionProfile simpleSessionProfile = SimpleApplication.getBeanByType(SimpleSessionProfile.class);
        if (simpleSessionProfile == null) {
            throw new GlobalException(SERVICE_NOT_EXIST);
        }
        String token = simpleSessionProfile.privilegeInfoLogin(request, response);
        return new ResBody().buildSuccessResBody(token, null, GlobalResponseCode.SYS_SUCCESS);
    }

    /**
     * 用户注销接口
     *
     * @param request
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = {"/logout"}, method = RequestMethod.POST)
    @ResponseBody
    public ResBody privilegeInfoLogout(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        // 获取已实现的接口
        if (SimpleApplication.isExistBean("simpleSessionProfile") == false) {
            // 未实现接口
            throw new GlobalException(SERVICE_NOT_EXIST);
        }
        SimpleSessionProfile simpleSessionProfile = SimpleApplication.getBeanByType(SimpleSessionProfile.class);
        if (simpleSessionProfile == null) {
            throw new GlobalException(SERVICE_NOT_EXIST);
        }
        simpleSessionProfile.privilegeInfoLogout(request, response);
        return new ResBody().buildSuccessResBody(null, null, GlobalResponseCode.SYS_SUCCESS);
    }
}
