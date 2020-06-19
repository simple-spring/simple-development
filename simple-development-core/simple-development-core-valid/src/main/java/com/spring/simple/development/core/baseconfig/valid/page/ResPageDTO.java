package com.spring.simple.development.core.baseconfig.valid.page;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 响应分页对象
 */
@Data
public class ResPageDTO<M> implements Serializable {
    /**
     * 开始页数
     */
    private Integer pageNum = 0;
    /**
     * 每页显示数量
     */
    private Integer pageSize = 10;

    /**
     * 总记录数
     */
    private long totalCount;
    /**
     * 响应数据
     */
    private List<M> list;
}
