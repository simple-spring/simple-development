package com.spring.simple.development.core.component.mvc.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @desc: 分页对象
 * @auth: hjs
 * @date: 2020/6/18 0018
 */
@Data
@ApiModel(description = "分页对象")
public class Pager<T> {

    @ApiModelProperty(value = "页数",example = "1")
    private int pageNo;

    @ApiModelProperty(value = "记录总数",example = "15")
    private int total;

    /**
     * 分页信息
     */
    private List<T> data;

    @ApiModelProperty(value = "每页展示数",example = "15")
    private int limit = 15;

    public int getStart() {
        // 分页开始值 关键
        if (pageNo == 0) {
            return 0;
        } else {
            return (pageNo - 1) * limit;
        }
    }
}
