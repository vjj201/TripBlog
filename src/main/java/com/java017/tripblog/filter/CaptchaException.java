package com.java017.tripblog.filter;

import org.springframework.security.core.AuthenticationException;

/**
 * @author YuCheng
 * @date 2021/10/19 - 下午 04:42
 */
public class CaptchaException extends AuthenticationException {
    public CaptchaException() {
        super("圖形驗證錯誤");
    }

    public CaptchaException(String detail) {
        super(detail);
    }

    public CaptchaException(String detail, Throwable ex) {
        super(detail, ex);
    }
}
