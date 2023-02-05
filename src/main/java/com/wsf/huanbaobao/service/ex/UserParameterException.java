package com.wsf.huanbaobao.service.ex;

public class UserParameterException extends ServiceException {
    public UserParameterException() {
        super();
    }

    public UserParameterException(String message) {
        super(message);
    }

    public UserParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserParameterException(Throwable cause) {
        super(cause);
    }

    protected UserParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
