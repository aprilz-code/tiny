package com.aprilz.tiny.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description: param
 * @author: liushaohui
 * @since: 2022/5/25
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class ApUseInfoParam  implements  Serializable{

        @NotBlank(message = "token不能为空")
        private  String token;

        /**
         * 用户名
         */
        @NotBlank(message = "用户名不能为空")
        private String username;

        /**
         * 手机号码
         */
        @NotBlank(message = "手机号码不能为空")
        private String phone;

        /**
         * 银行卡号
         */
        @NotBlank(message = "银行卡号不能为空")
        private String bankCard;

        /**
         * 开户行
         */
        @NotBlank(message = "开户行不能为空")
        private String openBank;

        /**
         * 单位名称
         */
        @NotBlank(message = "单位名称不能为空")
        private String companyName;

        /**
         * 工作地址
         */
        @NotBlank(message = "工作地址不能为空")
        private String workPlace;

        /**
         * 居住地
         */
        @NotBlank(message = "居住地不能为空")
        private String address;

        /**
         * 总额度
         */
        @NotNull(message = "额度不能为空")
        private BigDecimal totalAmount;

        /**
         * 有车：0->没有；1->有
         */
        private Integer hasCar;

        /**
         * 有公积金：0->没有；1->有
         */
        private Integer hasReservedFunds;

        /**
         * 有保单：0->没有；1->有
         */
        private Integer hasChit;

        /**
         *  关联人ID
         */
        private String relationId;

}
