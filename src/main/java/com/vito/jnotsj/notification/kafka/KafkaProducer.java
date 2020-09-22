package com.vito.jnotsj.notification.kafka;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.vito.jnotsj.common.kafkaProcessing.BaseKafkaMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate kafkaTemplate;

    @SneakyThrows
    @HystrixCommand
    public <T extends BaseKafkaMessage> void send(T data, String topic) {
        this.kafkaTemplate.send(topic, data).get();
    }
}