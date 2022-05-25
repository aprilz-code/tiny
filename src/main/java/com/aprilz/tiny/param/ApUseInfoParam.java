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

        @NotBlank
        private  String token;


        @NotNull
        private Long id;

        /**
         * 用户名
         */
        @NotBlank
        private String username;

        /**
         * 手机号码
         */
        @NotBlank
        private String phone;

        /**
         * 银行卡号
         */
        @NotBlank
        private String bankCard;

        /**
         * 开户行
         */
        @NotBlank
        private String openBank;

        /**
         * 单位名称
         */
        @NotBlank
        private String companyName;

        /**
         * 工作地址
         */
        @NotBlank
        private String workPlace;

        /**
         * 居住地
         */
        @NotBlank
        private String address;

        /**
         * 总额度
         */
        @NotBlank
        private BigDecimal totalAmount;

        /**
         * 有车：0->没有；1->有
         */
        @NotNull
        private Integer hasCar;

        /**
         * 有公积金：0->没有；1->有
         */
        @NotNull
        private Integer hasReservedFunds;

        /**
         * 有保单：0->没有；1->有
         */
        @NotNull
        private Integer hasChit;



}
