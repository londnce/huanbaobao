package com.wsf.huanbaobao.service.ex;

public class AdminNullException extends ServiceException {
    public AdminNullException() {
        super();
    }

    public AdminNullException(String message) {
        super(message);
    }

    public AdminNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdminNullException(Throwable cause) {
        super(cause);
    }

    protected AdminNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
