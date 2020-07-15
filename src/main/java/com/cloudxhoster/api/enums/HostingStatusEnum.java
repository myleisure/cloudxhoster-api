package com.cloudxhoster.api.enums;

public enum HostingStatusEnum {

    UP("UP"),
    DOWN("DOWN"),
    BLOCKED("BLOCKED"),
    IN_PROGRESS("IN_PROGRESS");

    private final String name;

    HostingStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
