package com.vito.jnotsj.mailEnitty;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public enum MailType {
    ATTENDED_NOTIFICATION("ATTENDED_NOTIFICATION");

    @Getter
    private String type;

    MailType(String type) {
        this.type = type;
    }
}
