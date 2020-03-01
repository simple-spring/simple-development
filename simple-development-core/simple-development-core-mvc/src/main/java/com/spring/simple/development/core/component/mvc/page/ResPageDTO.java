package com.spring.simple.development.core.component.mvc.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 响应分页对象
 */
@ApiModel(value="ResPageDTO对象",description="公共返回对分页对象")
public class ResPageDTO<M> implements Serializable{
	/**开始页数*/
	@ApiModelProperty(value = "开始页数",example = "0")
	private Integer pageNum = 0;
	/**每页显示数量*/
	@ApiModelProperty(value = "每页显示数量",example = "10")
	private Integer pageSize = 10;

	/** 总记录数  */
	@ApiModelProperty(value = "总记录数",example = "100")
	private long totalCount;
	/**  响应数据 */
	@ApiModelProperty(value = "响应数据")
	private List<M> list;

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public List<M> getList() {
		return list;
	}

	public void setList(List<M> list) {
		this.list = list;
	}
}
