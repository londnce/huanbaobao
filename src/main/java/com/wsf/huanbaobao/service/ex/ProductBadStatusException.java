package com.wsf.huanbaobao.service.ex;

public class ProductBadStatusException extends ServiceException {
    public ProductBadStatusException() {
        super();
    }

    public ProductBadStatusException(String message) {
        super(message);
    }

    public ProductBadStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductBadStatusException(Throwable cause) {
        super(cause);
    }

    protected ProductBadStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
