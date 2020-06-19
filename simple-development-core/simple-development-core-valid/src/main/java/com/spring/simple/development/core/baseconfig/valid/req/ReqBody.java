package com.spring.simple.development.core.baseconfig.valid.req;



import com.spring.simple.development.core.baseconfig.valid.page.ReqPageDTO;

import java.io.Serializable;
import java.util.Map;

/**
 * 请求报文消息
 */
public class ReqBody<T> implements Serializable {
	/**
	 * 请求参数
	 */
	private Map<String, Object> paramsMap;


	/**
	 * 分页对象
	 */
	private ReqPageDTO page;

	public Map<String, Object> getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(Map<String, Object> paramsMap) {
		this.paramsMap = paramsMap;
	}

	public ReqPageDTO getPage() {
		return page;
	}

	public void setPage(ReqPageDTO page) {
		this.page = page;
	}
}
