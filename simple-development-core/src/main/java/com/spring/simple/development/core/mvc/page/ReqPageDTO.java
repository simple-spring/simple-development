package com.spring.simple.development.core.mvc.page;

import java.io.Serializable;

/**
 * 请求分页对象
 */
public class ReqPageDTO<T> implements Serializable {
	/**开始页数*/
	private Integer startPage = 0;
	/**每页显示数量*/
	private Integer pageSize = 10;

	public Integer getStartPage() {
		return startPage;
	}

	public void setStartPage(Integer startPage) {
		this.startPage = startPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
