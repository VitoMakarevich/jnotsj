package com.vito.jnotsj.kafka;

import com.vito.jnotsj.mailEnitty.BaseEmailData;
import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    private static final String TOPIC = "users";
    @Autowired
    private KafkaTemplate<String, BaseEmailData> kafkaTemplate;
    public void sendMessage(BaseEmailData data){
        this.kafkaTemplate.send(TOPIC, data);
    }
}