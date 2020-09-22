package com.vito.jnotsj.auth.entity;

public enum RoleName {
    ADMIN("ADMIN"),
    USER("USER");

    private String name;

    RoleName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}