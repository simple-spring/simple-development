package com.spring.simple.development.core.component.mvc.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.lava.privilege.PrivilegeInfo;
import com.spring.simple.development.core.annotation.base.NoLogin;
import com.spring.simple.development.core.baseconfig.context.SimpleContentApplication;
import com.spring.simple.development.core.baseconfig.isapiservice.MethodParams;
import com.spring.simple.development.core.baseconfig.isapiservice.ServerFactory;
import com.spring.simple.development.core.baseconfig.isapiservice.ServiceInvoke;
import com.spring.simple.development.core.baseconfig.user.SimpleSessionProfile;
import com.spring.simple.development.core.component.mvc.req.ReqBody;
import com.spring.simple.development.core.component.mvc.req.RpcRequest;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import com.spring.simple.development.support.constant.VersionConstant;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.exception.GlobalResponseCode;
import com.spring.simple.development.support.utils.DateUtils;
import com.spring.simple.development.support.utils.HttpRequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
@Api(tags = "统一入口")
@RestController
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
    @ApiOperation(value = "HttpServletRequest", notes = "登录后接口服务")
    @RequestMapping(value = {"/api/{version}/{serviceName}/{methodName}", "/api", "/api/{version}"}, method = RequestMethod.POST)
    public ResBody api(@ApiParam("登录后接口服务请求参数") HttpServletRequest request, @PathVariable(name = "version", required = false) String version, @PathVariable(name = "serviceName", required = false) String serviceName, @PathVariable(name = "methodName", required = false) String methodName) throws Throwable {

        if (StringUtils.isEmpty(version)) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "版本号为空");
        }
        if (version.equals(VersionConstant.APP_VERSION_1) == false) {
            throw new GlobalException(RES_PARAM_INVALID, "版本号无效");
        }
        String paramJson = HttpRequestUtil.readStringFromRequestBody(request);

        logger.info("requestJson:" + paramJson + " date:" + DateUtils.getCurrentTime());

        RpcRequest rpcRequest = new RpcRequest();
        if (!StringUtils.isEmpty(serviceName) && !StringUtils.isEmpty(methodName)) {
            rpcRequest.setServiceName(serviceName);
            rpcRequest.setMethodName(methodName);
            ReqBody reqBody = new ReqBody();
            // 解析
            if (!StringUtils.isEmpty(paramJson)) {
                MethodParams methodParams = ServerFactory.serviceMethodMap.get(serviceName + "-" + methodName);
                if(methodParams == null){
                    throw new GlobalException(SERVICE_NOT_EXIST);
                }
                Object paramData = getParamData(methodParams.getMethodClass()[0], paramJson);
                Map<String, Object> paramsMap = new HashMap<>();
                paramsMap.put(methodParams.getKey()[0], paramData);
                reqBody.setParamsMap(paramsMap);
            }
            rpcRequest.setReqBody(reqBody);
        } else {
            // 解析
            if (StringUtils.isEmpty(paramJson)) {
                throw new GlobalException(RES_PARAM_IS_EMPTY, "服务器未收到信息");
            }
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
    @ApiOperation(value = "HttpServletRequest", notes = "非登录接口服务")
    @ApiParam(name = "请求参数")
    @NoLogin
    @RequestMapping(value = {"/config/{version}/{serviceName}/{methodName}", "/config", "/config/{version}"}, method = RequestMethod.POST)
    public ResBody config(@ApiParam("非登录接口服务请求参数") HttpServletRequest request, @PathVariable(name = "version", required = false) String version, @PathVariable(name = "serviceName", required = false) String serviceName, @PathVariable(name = "methodName", required = false) String methodName) throws Throwable {
        if (StringUtils.isEmpty(version)) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "版本号为空");
        }
        if (version.equals(VersionConstant.APP_VERSION_1) == false) {
            throw new GlobalException(RES_PARAM_INVALID, "版本号无效");
        }
        // 读取参数
        String paramJson = HttpRequestUtil.readStringFromRequestBody(request);
        logger.info("requestJson:" + paramJson + " date:" + DateUtils.getCurrentTime());

        RpcRequest rpcRequest = new RpcRequest();
        if (!StringUtils.isEmpty(serviceName) && !StringUtils.isEmpty(methodName)) {
            rpcRequest.setServiceName(serviceName);
            rpcRequest.setMethodName(methodName);
            ReqBody reqBody = new ReqBody();
            // 解析
            if (!StringUtils.isEmpty(paramJson)) {
                MethodParams methodParams = ServerFactory.serviceMethodMap.get(serviceName + "-" + methodName);
                if(methodParams == null){
                    throw new GlobalException(SERVICE_NOT_EXIST);
                }
                Object paramData = getParamData(methodParams.getMethodClass()[0], paramJson);
                Map<String, Object> paramsMap = new HashMap<>();
                paramsMap.put(methodParams.getKey()[0], paramData);
                reqBody.setParamsMap(paramsMap);
            }
            rpcRequest.setReqBody(reqBody);
        } else {
            // 解析
            if (StringUtils.isEmpty(paramJson)) {
                throw new GlobalException(RES_PARAM_IS_EMPTY, "服务器未收到信息");
            }
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
    @ApiOperation(value = "HttpServletRequest", notes = "用户登录接口")
    @ApiParam(name = "请求参数")
    @NoLogin
    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public ResBody privilegeInfoLogin(@ApiParam("用户登录接口请求参数") HttpServletRequest request, HttpServletResponse response) throws Throwable {
        // 获取已实现的接口
        if (SimpleContentApplication.isExistBean("simpleSessionProfile") == false) {
            // 未实现接口
            throw new GlobalException(SERVICE_NOT_EXIST);
        }
        SimpleSessionProfile simpleSessionProfile = SimpleContentApplication.getBeanByType(SimpleSessionProfile.class);
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
    @ApiOperation(value = "HttpServletRequest", notes = "用户注销接口")
    @ApiParam(name = "请求参数")
    @RequestMapping(value = {"/logout"}, method = RequestMethod.POST)
    public ResBody privilegeInfoLogout(@ApiParam("用户注销接口请求参数") HttpServletRequest request, HttpServletResponse response) throws Throwable {
        // 获取已实现的接口
        if (SimpleContentApplication.isExistBean("simpleSessionProfile") == false) {
            // 未实现接口
            throw new GlobalException(SERVICE_NOT_EXIST);
        }
        SimpleSessionProfile simpleSessionProfile = SimpleContentApplication.getBeanByType(SimpleSessionProfile.class);
        if (simpleSessionProfile == null) {
            throw new GlobalException(SERVICE_NOT_EXIST);
        }
        simpleSessionProfile.privilegeInfoLogout(request, response);
        return new ResBody().buildSuccessResBody(null, null, GlobalResponseCode.SYS_SUCCESS);
    }

    /**
     * 获取用户信息
     *
     * @param request
     * @return
     * @throws Throwable
     */
    @ApiOperation(value = "HttpServletRequest", notes = "获取用户信息")
    @ApiParam(name = "请求参数")
    @RequestMapping(value = {"/getUser"}, method = RequestMethod.POST)
    public ResBody privilegeInfoGetUser(@ApiParam("获取用户信息请求参数") HttpServletRequest request, HttpServletResponse response) throws Throwable {
        // 获取已实现的接口
        if (SimpleContentApplication.isExistBean("simpleSessionProfile") == false) {
            // 未实现接口
            throw new GlobalException(SERVICE_NOT_EXIST);
        }
        SimpleSessionProfile simpleSessionProfile = SimpleContentApplication.getBeanByType(SimpleSessionProfile.class);
        if (simpleSessionProfile == null) {
            throw new GlobalException(SERVICE_NOT_EXIST);
        }
        PrivilegeInfo privilegeInfo = simpleSessionProfile.getPrivilegeInfo(request, response, null);
        return new ResBody().buildSuccessResBody(privilegeInfo, null, GlobalResponseCode.SYS_SUCCESS);
    }
    
    /**
     * @author liko.wang
     * @Date 2020/4/8/008 14:46
     * @param paramClass
     * @param paramJson 
     * @return java.lang.Object
     * @Description 类型转换
     **/
    private Object getParamData(Class paramClass, Object paramJson){
        if (paramClass.isPrimitive()) {
            return paramJson;
        }
        return JSONObject.parseObject((String)paramJson, paramClass);
    }
}
