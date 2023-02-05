package com.wsf.huanbaobao.service.ex;

public class PasswordNotNullException extends ServiceException {
    public PasswordNotNullException() {
        super();
    }

    public PasswordNotNullException(String message) {
        super(message);
    }

    public PasswordNotNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordNotNullException(Throwable cause) {
        super(cause);
    }

    protected PasswordNotNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
