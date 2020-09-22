package com.vito.jnotsj.notification.service;

import com.vito.jnotsj.auth.entity.User;
import com.vito.jnotsj.notification.entity.NotificationAttempt;
import com.vito.jnotsj.notification.kafka.KafkaProducer;
import com.vito.jnotsj.notification.mailEnitty.BaseEmailData;
import com.vito.jnotsj.notification.mailEnitty.NotificationAttemptEmail;
import com.vito.jnotsj.notification.mailEnitty.NotificationAttemptedData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationMailService {
    private final KafkaProducer kafkaProducer;
    @Value("${spring.kafka.mail.topic}")
    private String mailTopic;


    public void sendUserNotificationAttemptedEmail(User user) {
        NotificationAttemptedData notificationAttemptedData = NotificationAttemptedData.builder()
                .username(user.getUsername())
                .build();
        NotificationAttemptEmail notificationAttemptEmail = NotificationAttemptEmail.builder()
                .content(notificationAttemptedData)
                .email(user.getEmail())
                .build();

        this.sendMail(notificationAttemptEmail);
    }

    private <T extends BaseEmailData> void sendMail(T data) {
        this.kafkaProducer.send(data, this.mailTopic);
    }
}
