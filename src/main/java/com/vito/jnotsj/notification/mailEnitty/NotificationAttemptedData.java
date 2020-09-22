package com.vito.jnotsj.notification.mailEnitty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.vito.jnotsj.notification.entity.NotificationAttempt;
import com.vito.jnotsj.notification.entity.NotificationData;
import com.vito.jnotsj.notification.vo.notificationData.NotificationDataVO;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationAttemptedData {
    private String username;
}
