package com.tom.exception;

import com.tom.api.IErrorCode;

public class ApiException extends RuntimeException{
    private IErrorCode errorCode;

    public ApiException(IErrorCode iErrorCode) {
        this.errorCode = iErrorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable throwable) {
        super(throwable);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }


}
