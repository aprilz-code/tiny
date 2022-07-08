package com.aprilz.tiny.common.exception;

import com.aprilz.tiny.common.api.ResultCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 全局业务异常类
 *
 * @author aprilz
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ServiceException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "网络错误，请稍后重试！";
    private static final long serialVersionUID = 3447728300174142127L;
    /**
     * 异常消息
     */
    private String msg = DEFAULT_MESSAGE;

    /**
     * 错误码
     */
    private ResultCode resultCode;

    public ServiceException(String msg) {
        this.resultCode = ResultCode.FAILED;
        this.msg = msg;
    }

    public ServiceException() {
        super();
    }

    public ServiceException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public ServiceException(ResultCode resultCode, String message) {
        this.resultCode = resultCode;
        this.msg = message;
    }

}
