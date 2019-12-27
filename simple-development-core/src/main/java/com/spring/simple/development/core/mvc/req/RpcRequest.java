package com.spring.simple.development.core.mvc.req;

import java.io.Serializable;

/**
 * request
 */
public class RpcRequest implements Serializable{
	private static final long serialVersionUID = 1L;
	

    private String serviceName;
    private String methodName;
	private ReqBody reqBody;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public ReqBody getReqBody() {
		return reqBody;
	}

	public void setReqBody(ReqBody reqBody) {
		this.reqBody = reqBody;
	}
}
