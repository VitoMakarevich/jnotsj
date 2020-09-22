package com.vito.jnotsj.notification.mailEnitty;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MailType {
    ATTENDED_NOTIFICATION("ATTENDED_NOTIFICATION");

    @Getter
    private String type;

    MailType(String type) {
        this.type = type;
    }
}
