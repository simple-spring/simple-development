package com.spring.simple.development.core.component.mvc.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 请求分页对象
 */
@ApiModel(value="ReqPageDTO对象",description="请求分页对象")
public class ReqPageDTO<T> implements Serializable {
	/**开始页数*/
	@ApiModelProperty(value = "开始页数",example = "0")
	private Integer startPage = 0;
	/**每页显示数量*/
	@ApiModelProperty(value = "每页显示数量",example = "10")
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
