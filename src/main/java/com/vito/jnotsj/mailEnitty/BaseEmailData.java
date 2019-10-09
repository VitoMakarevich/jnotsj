package com.vito.jnotsj.mailEnitty;

import lombok.Data;

@Data
public abstract class BaseEmailData<T> {
    private String email;

    private MailType type;

    private String subject;

    private T content;
}
