package com.spring.simple.development.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author luke
 */
@ApiModel(value = "demoVo", description = "demo描述")
@Data
public class DemoVo implements Serializable {
    @ApiModelProperty(value = "id", example = "1")
    @NotBlank(message = "id cannot be empty")
    private Long id;
    @ApiModelProperty(value = "用户名", example = "wl")
    @NotBlank(message = "name cannot be empty")
    private String name;
    @ApiModelProperty(value = "密码", example = "123456")
    @NotBlank(message = "password cannot be empty")
    private String password;
}
