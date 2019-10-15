package com.vito.jnotsj.kafka;

import com.vito.jnotsj.mailEnitty.BaseEmailData;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
@Slf4j
public class KafkaProducer {
    @Value("${spring.kafka.mail.topic}")
    private String mailTopic;
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendMessage(BaseEmailData data){
        ListenableFuture listenableFuture = this.kafkaTemplate.send(mailTopic, data);
        listenableFuture.addCallback(new KafkaProducerCallback());
    }
}