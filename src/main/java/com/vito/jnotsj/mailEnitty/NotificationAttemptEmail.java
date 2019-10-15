package com.vito.jnotsj.mailEnitty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class NotificationAttemptEmail extends BaseEmailData<NotificationAttemptedData> {
    {
        this.type = MailType.ATTENDED_NOTIFICATION;
    }
}
