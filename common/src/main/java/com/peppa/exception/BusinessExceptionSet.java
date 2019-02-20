package com.peppa.exception;

public enum BusinessExceptionSet {

    ERROR1(711, "ERROR1"),
    ERROR2(712, "ERROR2"),
    ERROR3(713, "ERROR3");

    private String message;
    private Integer code;

    BusinessExceptionSet(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
