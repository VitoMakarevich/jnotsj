package com.vito.jnotsj.notification.kafka;

import com.vito.jnotsj.notification.mailEnitty.NotificationAttemptEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaMailConsumer {
    @KafkaListener(topics = "${spring.kafka.mail.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenForNotificationAttemptEmail(
            @Payload NotificationAttemptEmail message,
            Acknowledgment acknowledgment) {
        log.debug("Message received " + message.getSubject());
    }
}
