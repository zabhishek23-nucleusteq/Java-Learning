package com.example.notification.Config;

import com.example.notification.DTO.EmployeeDto;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.*;

@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public KafkaConsumerConfig() {
    }

    @Bean
    public ConsumerFactory<String, EmployeeDto> consumerFactory() {
        JsonDeserializer<EmployeeDto> deserializer = new JsonDeserializer<>(EmployeeDto.class);
        deserializer.setRemoveTypeHeaders(true);
        deserializer.addTrustedPackages(new String[]{"*"});
        deserializer.setUseTypeMapperForKey(false);
        Map<String, Object> config = new HashMap<>();
        config.put("bootstrap.servers", this.bootstrapServers);
        config.put("group.id", "notification-group");
        config.put("key.deserializer", StringDeserializer.class);
        config.put("value.deserializer", JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EmployeeDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, EmployeeDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(this.consumerFactory());
        return factory;
    }
}
