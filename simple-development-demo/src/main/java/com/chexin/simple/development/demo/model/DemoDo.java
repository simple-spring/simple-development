package com.spring.simple.development.demo.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 *
 * @author liko
 * @date   2019/12/21
 */
@Data
public class DemoDo implements Serializable {
    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String value;

    private static final long serialVersionUID = 1L;
}