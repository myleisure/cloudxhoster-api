package com.cloudxhoster.api.enums;

public enum BusinessErrorCodeEnum {
    ITEM_NOT_FOUND(10000),
    ITEM_ALREADY_EXISTS(10001),
    ITEM_SAVW_EXCEPTION(10002),
    UPLOADING_ERROR(10003),
    WEBSITE_EXTRACTION_ERROR(10004);


    private int code;

    BusinessErrorCodeEnum(int code) {
        this.code = code;
    }

    public int getCode() {return this.code;}
}
