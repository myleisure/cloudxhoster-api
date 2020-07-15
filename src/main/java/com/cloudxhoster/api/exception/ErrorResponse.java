package com.cloudxhoster.api.exception;

public class ErrorResponse {

    String errorMessage;
    int errorCode;

    public ErrorResponse(String errorMessage, int errorCode) {
        super();
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }
}