package com.vito.jnotsj.mailEnitty;

public enum MailType {
    ATTENDED_NOTIFICATION("ATTENDED_NOTIFICATION");

    private String type;

    MailType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
