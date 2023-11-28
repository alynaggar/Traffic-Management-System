package com.example.tms.Entity.Response;

public class ResponseEntity<T> {

    private String message;
    private T data;
    private int code;

    public ResponseEntity(ResponseCode ResponseCode, T data) {
        this.message = ResponseCode.getMessage();
        this.data = data;
        this.code = ResponseCode.getCode();
    }

    public ResponseEntity(String message, T data, int code) {
        this.message = message;
        this.data = data;
        this.code = code;
    }

    public ResponseEntity(ResponseCode ResponseCode) {
        this.message = ResponseCode.getMessage();
        this.code = ResponseCode.getCode();
    }

    public ResponseEntity(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
