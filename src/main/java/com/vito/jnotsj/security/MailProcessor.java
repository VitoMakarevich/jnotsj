package com.vito.jnotsj.security;

import com.vito.jnotsj.mailEnitty.NotificationAttemptEmail;
import com.vito.jnotsj.service.NotificationAttemptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MailProcessor {
    public void processAttemptMessage(NotificationAttemptEmail notificationAttemptEmail) {
        log.debug(notificationAttemptEmail.toString());
    }
}
