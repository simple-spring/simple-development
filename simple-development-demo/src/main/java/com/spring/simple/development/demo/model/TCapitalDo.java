package com.spring.simple.development.demo.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 资方信息表
 * </p>
 *
 * @author luke
 * @since 2020-07-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_capital")
public class TCapitalDo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 是否删除
     */
    private String isDeleted;

    /**
     * 资方名称
     */
    @TableField("capitalName")
    private String capitalName;

    /**
     * 资方代码
     */
    @TableField("capitalCode")
    private String capitalCode;

    /**
     * 社会信用代码
     */
    @TableField("creditCode")
    private String creditCode;

    /**
     * 法人
     */
    @TableField("legalName")
    private String legalName;

    /**
     * 法人身份证号
     */
    @TableField("legalCardNo")
    private String legalCardNo;

    /**
     * 联系人姓名
     */
    @TableField("linkName")
    private String linkName;

    /**
     * 联系电话
     */
    @TableField("linkPhone")
    private String linkPhone;

    /**
     * 联系邮箱
     */
    @TableField("linkEmail")
    private String linkEmail;

    /**
     * 联系地址
     */
    @TableField("linkAddress")
    private String linkAddress;

    /**
     * 银行卡号
     */
    @TableField("bankNo")
    private String bankNo;

    /**
     * 银行
     */
    @TableField("bankName")
    private String bankName;

    /**
     * 开户行
     */
    @TableField("bankOpening")
    private String bankOpening;

    /**
     * 合同协议保存位置
     */
    @TableField("agreementUrl")
    private String agreementUrl;

    /**
     * 状态：1-正常;2-冻结;3-注销
     */
    private Integer status;

    /**
     * 保证金退回方式：1-期末抵扣 2-结清退回 3-抵扣最后一期
     */
    @TableField("bondBackType")
    private Integer bondBackType;


}
