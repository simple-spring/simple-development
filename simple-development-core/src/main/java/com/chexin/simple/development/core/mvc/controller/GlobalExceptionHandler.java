package com.chexin.simple.development.core.mvc.controller;


import com.alibaba.fastjson.JSON;
import com.chexin.simple.development.core.log.ErrorMessage;
import com.chexin.simple.development.core.mvc.res.ResBody;
import com.chexin.simple.development.core.utils.DateUtils;
import com.chexin.simple.development.core.utils.GzipUtil;
import com.chexin.simple.development.core.utils.IpUtil;
import com.chexin.simple.development.core.utils.PrimaryKeyGenerator;
import com.chexin.simple.development.support.GlobalException;
import com.chexin.simple.development.support.GlobalResponseCode;
import com.chexin.simple.development.support.NoPermissionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理，由spring mvc拦截异常，做统一的处理
 */
@ControllerAdvice
public class GlobalExceptionHandler extends DefaultHandlerExceptionResolver {
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);
    private static final Logger errorLogMessageLogger = LogManager.getLogger("errorLogMessage");

    @ExceptionHandler(value = Throwable.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResBody processUnauthenticatedException(HttpServletRequest request, HttpServletResponse response, Throwable e) {
        logger.error(DateUtils.getCurrentTime() + e.getMessage(), e);
        // 无权限处理,重定向至无权限页面,
        if (e instanceof NoPermissionException) {
            //告诉ajax我是重定向
            response.setHeader("REDIRECT", "PERMISSION");
            //告诉ajax我重定向的路径
            response.setHeader("URL", request.getContextPath() + "/static/noPermission.html");
            return ResBody.buildFailResBody(new GlobalResponseCode(((NoPermissionException) e).getErrorStatus(), ((NoPermissionException) e).getErrorCode(), ((NoPermissionException) e).getContent()));
        }
        // 业务异常处理
        if (e instanceof GlobalException) {
            return ResBody.buildFailResBody(new GlobalResponseCode(((GlobalException) e).getErrorStatus(), ((GlobalException) e).getErrorCode(), ((GlobalException) e).getContent()));
        }
        // 收集错误日志
        collectionLog(request, e);
        return ResBody.buildFailResBody();
    }

    private void collectionLog(HttpServletRequest request, Throwable e) {
        try {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setLogId(PrimaryKeyGenerator.getInstance().nextId().toString());
            errorMessage.setProjectType("api");
            errorMessage.setLogType("restful api");
            errorMessage.setIp(IpUtil.getIp());
            errorMessage.setLogPath("/data/logs/manila-api/error/error.log");
            errorMessage.setDate(DateUtils.getCurrentTime());
            errorMessage.setContent(GzipUtil.compressBase64(JSON.toJSONString(e)));
            errorMessage.setDescription(e+"");
            errorMessage.setUrl(request.getRequestURI());
            errorMessage.setRemoteIp(request.getRemoteHost());
            errorLogMessageLogger.info(JSON.toJSONString(errorMessage));
        } catch (Exception ex) {
            logger.error("收集错误日志错误:", e);
        }
    }
}