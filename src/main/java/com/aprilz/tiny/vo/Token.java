package com.aprilz.tiny.vo;

import lombok.Data;

/**
 * Token 实体类
 *
 * @author aprilz
 * @version v1.0
 * 2020-11-13 10:02
 */
@Data
public class Token {
    /**
     * 访问token
     */
    private String token;

    /**
     * 刷新token
     */
    private String refreshToken;

}
