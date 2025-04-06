package com.tauhoa.train.dtos.response;


public class ErrorResponse {
    private String message;
    private Object data;

    public ErrorResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    // Getter và Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
