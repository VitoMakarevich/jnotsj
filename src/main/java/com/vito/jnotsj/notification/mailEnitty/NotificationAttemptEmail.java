package com.vito.jnotsj.notification.mailEnitty;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class NotificationAttemptEmail extends BaseEmailData<NotificationAttemptedData> {
    private final MailType type = MailType.ATTENDED_NOTIFICATION;

    private final String subject = "Notification attempt";
}
