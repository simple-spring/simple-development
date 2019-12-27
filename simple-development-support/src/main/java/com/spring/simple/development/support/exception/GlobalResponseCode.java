package com.spring.simple.development.support.exception;

import java.io.Serializable;

/**
 * @author liko.wang
 * @Date 2019/12/18/018 14:20
 * @Description 全局响应码
 **/
public class GlobalResponseCode implements Serializable{
	
	private static final long serialVersionUID = 2862665858875203182L;

	/**
	 * 业务错误状态码
	 */
	public static final int BIZ_ERROR_STATUS = 0;
	
	/**
	 * 通用业务异常错误码
	 */
	private static final String SYS_ERROR_CODE = "SYS_ERROR";
	private static final String SYS_OK = "SYS_OK";
	private static final String SYS_NOT_LOGIN = "SYS_NOT_LOGIN";
	private static final String SERVICE_NO_FOUND = "SERVICE_NO_FOUND";
	private static final String SERVICE_INVOKE_ERROR = "SERVICE_INVOKE_ERROR";
	private static final String NOT_PERMISSION = "NO_PERMISSION";

	/**
	 * 构件通用业务错误对象
	 * 
	 */
	public static GlobalResponseCode buildBizErrorConst(String errMsg){
		return new GlobalResponseCode(SYS_ERROR_CODE, errMsg);
	}
	
	/*************************************注意：非系统异常不允许自定义status**************************/
	public static final GlobalResponseCode SYS_FAILED = new GlobalResponseCode(0, SYS_ERROR_CODE, "系统太忙了，请稍后再试！");
	public static final GlobalResponseCode SYS_SUCCESS = new GlobalResponseCode(1, SYS_OK, "请求成功！");
	public static final GlobalResponseCode SYS_NO_LOGIN = new GlobalResponseCode(2, SYS_NOT_LOGIN, "未登录!");
	public static final GlobalResponseCode SERVICE_NOT_EXIST = new GlobalResponseCode(3, SERVICE_NO_FOUND, "服务未找到!");
	public static final GlobalResponseCode SERVICE_FAILED = new GlobalResponseCode(4, SERVICE_INVOKE_ERROR, "服务调用失败!");
	public static final GlobalResponseCode NO_PERMISSION = new GlobalResponseCode(5, NOT_PERMISSION, "没有权限!");

	
	/**************************************业务异常，可在子类定义自定义*******************************/
	public static final GlobalResponseCode EXAMPLE_EXCEPTION = new GlobalResponseCode("EXAMPLE_EXCEPTION", "异常自定义示例%s");

	private int status;
	private String code;
	private String message;
	
	public GlobalResponseCode(int status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
	
	public GlobalResponseCode(String code, String message) {
		this.status = BIZ_ERROR_STATUS;
		this.code = code;
		this.message = message;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public GlobalResponseCode changeMessage(String message) {
		this.message = message;
		return this;
	}
	
	/**
	 * 替换提示信息中的转换符
	 * 
	 */
	public GlobalResponseCode format (String... formatArgs) {
		if(formatArgs != null && formatArgs.length > 0) {
			this.message = String.format(this.message, (Object[]) formatArgs);
		}
		
		return this;
	}
}
