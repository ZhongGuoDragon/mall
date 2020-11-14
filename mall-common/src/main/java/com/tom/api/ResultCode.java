package com.tom.api;

/**
 *
 */
public enum ResultCode implements IErrorCode{
    SUCCESS(200, "succcess"),
    FAILED(500, "failed"),
    VALIDATE_FAILED(404, "parameter wrong"),
    UNAUTHORIZED(401, "unauthorized or token failure"),
    FORBIDDEN(403, "no permission");
    private long code;
    private String message;
    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public long getCode() {
        return code;
    }
}
