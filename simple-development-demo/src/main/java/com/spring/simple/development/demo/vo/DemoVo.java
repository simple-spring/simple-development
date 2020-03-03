package com.spring.simple.development.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 */
@ApiModel(value = "demoVo", description = "demo描述")
@Data
public class DemoVo {
    @ApiModelProperty(value = "用户名", example = "wl")
    @NotBlank(message = "isDelete cannot be empty")
    private String name;
    @ApiModelProperty(value = "密码", example = "123456")
    private String password;
}
