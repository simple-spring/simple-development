package com.spring.simple.development.core.component.mvc.res;


import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.support.exception.GlobalResponseCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import static com.spring.simple.development.support.exception.GlobalResponseCode.SYS_SUCCESS;


/**
 * 响应报文消息
 *
 * @author liko wang
 */
@ApiModel(value="ResBody对象",description="响应报文消息")
public class ResBody<T> implements Serializable {
    /**
     * 请求响应状态
     */
    @ApiModelProperty(value = "请求响应状态",example = "SYS_OK")
    private int status;
    /**
     * 请求响应码
     */
    @ApiModelProperty(value = "请求响应码",example = "1")
    private String code;
    /**
     * 请求响应消息
     */
    @ApiModelProperty(value = "请求响应码",example = "请求成功")
    private String message;
    /**
     * 请求响应数据对象
     */
    @ApiModelProperty(value = "请求响应数据对象")
    private T content;

    /**
     * 请求响应分页数据对象
     */
    @ApiModelProperty(value = "请求响应分页数据对象")
    private ResPageDTO<T> page;

    public ResBody() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ResPageDTO getPage() {
        return page;
    }

    public void setPage(ResPageDTO page) {
        this.page = page;
    }

    public ResBody buildSuccessResBody() {
        return buildSuccessResBody(null, null, SYS_SUCCESS);
    }

    public ResBody buildSuccessResBody(T content) {
        return buildSuccessResBody(content, null, SYS_SUCCESS);
    }

    public ResBody buildSuccessResBody(ResPageDTO<T> page) {
        return buildSuccessResBody(null, page, SYS_SUCCESS);
    }

    public ResBody buildSuccessResBody(T content, ResPageDTO<T> page) {
        return buildSuccessResBody(content, page, SYS_SUCCESS);
    }

    public ResBody<T> buildSuccessResBody(T content, ResPageDTO<T> page, GlobalResponseCode successConst) {
        ResBody res = new ResBody();
        res.setStatus(successConst.getStatus());
        res.setCode(successConst.getCode());
        res.setMessage(successConst.getMessage());

        if (content != null) {
            res.setContent(content);
        }

        if (page != null) {
            res.setPage(page);
        }
        return res;
    }

    public static ResBody buildFailResBody() {
        return buildFailResBody(GlobalResponseCode.SYS_FAILED);
    }

    /**
     * 构建失败报文头
     */
    public static ResBody buildFailResBody(GlobalResponseCode errorConst) {
        ResBody res = new ResBody();
        res.setStatus(errorConst.getStatus());
        res.setCode(errorConst.getCode());
        res.setMessage(errorConst.getMessage());
        return res;
    }

    /**
     * 构建自定义错误文本信息
     */
    public static ResBody buildFailTextResBody(String code, String message) {
        ResBody res = new ResBody();
        res.setStatus(GlobalResponseCode.SYS_FAILED.getStatus());
        res.setCode(code);
        res.setMessage(message);
        return res;
    }
}
