package com.example.tms.Entity.Response;

public enum ResponseCode {

    SUCCESS(200, "Success"),
    NOT_FOUND(404, "Content doesn't exist");

    private int code;
    private String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
