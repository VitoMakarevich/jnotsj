package com.vito.jnotsj.notification.mappers;

import com.vito.jnotsj.notification.entity.NotificationAttempt;
import com.vito.jnotsj.notification.vo.notificationAttempt.NotificationAttemptVO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class NotificationAttemptMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public NotificationAttemptVO entityToVo(NotificationAttempt notificationAttempt) {
        NotificationAttemptVO notificationAttemptVO = modelMapper.map(notificationAttempt, NotificationAttemptVO.class);

        return notificationAttemptVO;
    }
}
