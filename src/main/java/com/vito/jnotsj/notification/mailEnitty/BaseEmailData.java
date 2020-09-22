package com.vito.jnotsj.notification.mailEnitty;

import com.vito.jnotsj.common.kafkaProcessing.BaseKafkaMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BaseEmailData<T> extends BaseKafkaMessage {
    private String email;

    protected MailType type;

    protected String subject;

    protected T content;
}
