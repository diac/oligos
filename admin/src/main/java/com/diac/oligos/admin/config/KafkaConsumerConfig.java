package com.diac.oligos.admin.config;

import com.diac.oligos.domain.model.Formulation;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @Value("${spring.kafka.consumer.group-id}")
    private String kafkaGroupId;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
        return props;
    }

    @Bean
    public ConsumerFactory<Integer, Formulation> formulationConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Integer, Formulation> formulationKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, Formulation> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(formulationConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<Integer, List<Formulation>> formulationListConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Integer, List<Formulation>> formulationListKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, List<Formulation>> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(formulationListConsumerFactory());
        return factory;
    }
}