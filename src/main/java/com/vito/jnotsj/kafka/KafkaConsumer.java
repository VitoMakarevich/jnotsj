package com.vito.jnotsj.kafka;

import com.vito.jnotsj.mailEnitty.BaseEmailData;
import com.vito.jnotsj.mailEnitty.NotificationAttemptEmail;
import com.vito.jnotsj.mailEnitty.NotificationAttemptedData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "users", groupId = "${spring.kafka.consumer.group-id}")
    public void listenWithHeaders(
            @Payload NotificationAttemptEmail message,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info(
                "Received Message: " + message.toString() + message.getEmail()
                        + "from partition: " + partition);
    }
}
