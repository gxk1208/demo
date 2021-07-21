package com.auto.demo.common;

public class ExcelResult {
    private Object data;
    private String message;
    private Boolean isException;

    public ExcelResult(String message, Boolean isException) {
        this.message = message;
        this.isException = isException;
    }

    public ExcelResult(Object data, String message, Boolean isException) {
        this.data = data;
        this.message = message;
        this.isException = isException;
    }

    public Boolean getIsException() {
        return isException;
    }

    public void setIsException(Boolean exception) {
        isException = exception;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
