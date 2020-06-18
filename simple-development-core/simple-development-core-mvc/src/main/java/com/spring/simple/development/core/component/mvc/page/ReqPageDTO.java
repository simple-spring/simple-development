package com.spring.simple.development.core.component.mvc.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 请求分页对象
 */
@Data
@ApiModel(value = "ReqPageDTO对象", description = "请求分页对象")
public class ReqPageDTO<T> implements Serializable {
    /**
     * 开始页数
     */
    @ApiModelProperty(value = "开始页数", example = "1")
    @NotNull(message = "分页参数不能为空")
    private Integer startPage = 1;
    /**
     * 每页显示数量
     */
    @NotNull(message = "分页参数不能为空")
    @ApiModelProperty(value = "每页显示数量", example = "10")
    private Integer pageSize = 10;
}
