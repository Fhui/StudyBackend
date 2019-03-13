package com.harry.common;

/**
 * @author harry
 * @create 2019-03-12 16:15
 **/
public class BaseException extends RuntimeException {

    private ResponseCode code;

    public BaseException(ResponseCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public BaseException(ResponseCode code, String message) {
        super(message);
        this.code = code;
    }

    public ResponseCode getCode() {
        return code;
    }

    public void setCode(ResponseCode code) {
        this.code = code;
    }
}
