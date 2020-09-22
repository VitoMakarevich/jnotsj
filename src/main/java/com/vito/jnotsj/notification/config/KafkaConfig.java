package com.vito.jnotsj.notification.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@EnableKafka
@Configuration
@EnableConfigurationProperties( KafkaProperties.class )
@RequiredArgsConstructor
public class KafkaConfig {
    private final KafkaProperties properties;

    private static ObjectMapper objectMapper()
    {
        return new Jackson2ObjectMapperBuilder()
                .modules( new JavaTimeModule(), new Jdk8Module() )
                .serializationInclusion( JsonInclude.Include.NON_EMPTY )
                .featuresToEnable( DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT )
                .featuresToDisable( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, SerializationFeature.WRITE_DATES_AS_TIMESTAMPS )
                .build();
    }

    @Bean
    public ConsumerFactory<Object, Object> consumerFactory() {
        return new DefaultKafkaConsumerFactory(properties.buildConsumerProperties());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<?, ?>
    kafkaListenerContainerFactory(ConsumerFactory<Object, Object> consumerFactory) {

        ConcurrentKafkaListenerContainerFactory<Object, Object> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setMessageConverter( new StringJsonMessageConverter( objectMapper() ) );
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }

    @Bean
    public ProducerFactory<?, ?> kafkaProducerFactory()
    {
        final JsonSerializer<Object> jsonSerializer = new JsonSerializer<>( objectMapper() );
        // do not need type info in kafka headers, as deserialization will be based on @KafkaListener method param class
        jsonSerializer.setAddTypeInfo( false );

        return new DefaultKafkaProducerFactory<>( properties.buildProducerProperties() , null, jsonSerializer );
    }
}