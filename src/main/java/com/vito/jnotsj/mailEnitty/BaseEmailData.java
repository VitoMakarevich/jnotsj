package com.vito.jnotsj.mailEnitty;

import lombok.Data;

@Data
public class BaseEmailData<T> {
    protected String email;

    protected MailType type;

    protected String subject;

    protected T content;
}
