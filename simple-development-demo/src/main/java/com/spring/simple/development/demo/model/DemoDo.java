package com.spring.simple.development.demo.model;

import com.alibaba.lava.base.LavaDo;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * demo
 * </p>
 *
 * @author luke
 * @since 2020-07-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_demo")
public class DemoDo extends LavaDo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键Id
     */
    private Long id;

    /**
     * 账户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;


    @Override
    public String getBoQualifiedIntfName() {
        return null;
    }
}
