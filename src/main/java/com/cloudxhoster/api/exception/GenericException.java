package com.cloudxhoster.api.exception;

import com.cloudxhoster.api.enums.BusinessErrorCodeEnum;

public class GenericException extends RuntimeException {

    private final BusinessErrorCodeEnum businessErrorCodeEnum;

    public GenericException(String message, BusinessErrorCodeEnum businessErrorCodeEnum) {
        super(message);
        this.businessErrorCodeEnum = businessErrorCodeEnum;
    }

    public BusinessErrorCodeEnum getBusinessErrorCodeEnum() {
        return businessErrorCodeEnum;
    }
}