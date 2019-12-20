package com.chexin.simple.development.demo.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 *
 * @author liko
 * @date   2019/12/20
 */
@Data
public class CollectionRecordDo implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建人Id
     */
    private Integer creatorId;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改人Id
     */
    private Integer modifierId;

    /**
     * 客户姓名
     */
    private String customerName;

    /**
     * 客户手机号
     */
    private String phone;

    /**
     * 联系结果:0.答应还款、1.过期、2.挂掉、3.打不通、4.不还款
     */
    private String status;

    /**
     * 联系结果:0.答应还款、1.过期、2.挂掉、3.打不通、4.不还款',
     */
    private String statusName;

    /**
     * 记录
     */
    private String record;

    /**
     * 还款日期（年月日）
     */
    private String repaymentDate;

    /**
     * 还款时间段
00:00-12:00

12:00-14:00

14:00-16:00

16:00-18:00

18:00-00:00
     */
    private String repaymentPeriod;

    /**
     * 联系人姓名
     */
    private String recordName;

    /**
     * 联系人手机号
     */
    private String recordPhone;

    /**
     * 催收记录类型
1：本人
2.家人
3.同事
4.通讯录
     */
    private String recordRelation;

    /**
     * 催收类型描述
1：客户
2：家人
3：同事
4:通讯录
     */
    private String recordRelationName;

    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 电催人员ID
     */
    private Long electricManId;

    /**
     * 电催人员名称
     */
    private String electricManName;

    private static final long serialVersionUID = 1L;
}