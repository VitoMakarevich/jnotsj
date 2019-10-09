package com.vito.jnotsj.mailEnitty;

import com.vito.jnotsj.entity.NotificationData;
import com.vito.jnotsj.entity.User;
import com.vito.jnotsj.vo.notificationData.NotificationDataVO;
import lombok.Data;

@Data
public class NotificationAttemptedData {
    private NotificationDataVO notificationDataVO;

    private String username;
}
