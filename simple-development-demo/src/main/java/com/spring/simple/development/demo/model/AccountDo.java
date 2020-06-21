package com.spring.simple.development.demo.model;

import com.alibaba.lava.base.LavaDo;
import lombok.Data;

import java.util.Date;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：tt_sso_account
 *
 * @author liko
 * @date   2020/06/21
 */
@Data
public class AccountDo extends LavaDo {
    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private String createOwner;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    private String updateOwner;

    /**
     * 
     */
    private Long optimistic;

    /**
     * 
     */
    private String accountCode;

    /**
     * 
     */
    private String accountPwd;

    /**
     * 
     */
    private String cellphone;

    /**
     * 
     */
    private String parentAccountId;

    /**
     * 
     */
    private Byte status;

    /**
     * 
     */
    private String appKey;

    @Override
    public String getBoQualifiedIntfName() {
        return "com.spring.simple.development.demo.service.AccountBo";
    }
}