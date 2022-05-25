package com.aprilz.tiny.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author aprilz
 * @since 2022-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ap_use_info")
public class ApUseInfo extends Model<ApUseInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 银行卡号
     */
    private String bankCard;

    /**
     * 开户行
     */
    private String openBank;

    /**
     * 单位名称
     */
    private String companyName;

    /**
     * 工作地址
     */
    private String workPlace;

    /**
     * 居住地
     */
    private String address;

    /**
     * 总额度
     */
    private BigDecimal totalAmount;

    /**
     * 有车：0->没有；1->有
     */
    private Integer hasCar;

    /**
     * 有公积金：0->没有；1->有
     */
    @TableField("has_RESERVED_FUNDS")
    private Integer hasReservedFunds;

    /**
     * 有保单：0->没有；1->有
     */
    private Integer hasChit;

    /**
     * 身份证正面
     */
    private String front;

    /**
     * 身份证背面
     */
    private String behind;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
