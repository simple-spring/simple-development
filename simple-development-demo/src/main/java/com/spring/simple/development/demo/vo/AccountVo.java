package com.spring.simple.development.demo.vo;

import com.spring.simple.development.core.component.data.process.annotation.external.SimpleDpo;
import com.spring.simple.development.core.component.data.process.enums.OperateTypeEnum;
import com.spring.simple.development.demo.model.AccountDo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author liko
 * @description
 * @date 2020/06/21
 */
@Data
@ApiModel(value = "accountVo", description = "accountVo描述")
@SimpleDpo(tableName = "tt_sso_account", operateTypeEnum = OperateTypeEnum.SELECT, dataModelType = "simpleDataProcessExecutor", returnClass = AccountDo.class)
public class AccountVo {
    /**
     * 订单号
     */
//    @Condition(ConditionType = ConditionTypeEnum.AND)
    @ApiModelProperty(value = "id", example = "1")
    Long id;
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
}