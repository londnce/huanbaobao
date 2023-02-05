package com.wsf.huanbaobao.service.ex;

public class AdminErrorException extends ServiceException {
    public AdminErrorException() {
        super();
    }

    public AdminErrorException(String message) {
        super(message);
    }

    public AdminErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdminErrorException(Throwable cause) {
        super(cause);
    }

    protected AdminErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
