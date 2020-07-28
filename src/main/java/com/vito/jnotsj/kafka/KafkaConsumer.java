package com.vito.jnotsj.kafka;

import com.vito.jnotsj.mailEnitty.NotificationAttemptEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {
    @KafkaListener(topics = "${spring.kafka.mail.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenWithHeaders(
            @Payload NotificationAttemptEmail message,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
            Acknowledgment acknowledgment) {
        log.trace("Message received " + message.toString());
        acknowledgment.acknowledge();
    }
}
