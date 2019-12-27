package com.spring.simple.development.core.component.mvc.page;

import java.io.Serializable;
import java.util.List;

/**
 * 响应分页对象
 */
public class ResPageDTO<T> implements Serializable{
	/**开始页数*/
	private Integer pageNum = 0;
	/**每页显示数量*/
	private Integer pageSize = 10;
	
	/** 总记录数  */
	private long totalCount;
	/**  响应数据 */
	private List<T> list;

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

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
