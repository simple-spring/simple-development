package com.spring.simple.development.core.baseconfig.valid.page;

import lombok.Data;

import java.io.Serializable;

/**
 * 请求分页对象
 */
@Data
public class ReqPageDTO<T> implements Serializable {
    /**
     * 开始页数
     */
    private Integer startPage = 1;
    /**
     * 每页显示数量
     */
    private Integer pageSize = 10;
}
