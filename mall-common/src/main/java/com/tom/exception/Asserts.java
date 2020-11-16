package com.tom.exception;

import com.tom.api.IErrorCode;

/**
 * 断言处理类用于抛出各种异常
 * Created by tom on 2020/11/11
 */
public class Asserts {
    public static void fail(String message){
        throw new ApiException(message);
    }
    public static void fail(IErrorCode iErrorCode) {
        throw new ApiException(iErrorCode);
    }
}
