package com.wsf.huanbaobao.service.ex;

public class CateException extends ServiceException {
    public CateException() {
        super();
    }

    public CateException(String message) {
        super(message);
    }

    public CateException(String message, Throwable cause) {
        super(message, cause);
    }

    public CateException(Throwable cause) {
        super(cause);
    }

    protected CateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
