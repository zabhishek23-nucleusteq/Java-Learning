package com.example.notification_service.config;

import com.example.notification_service.dto.OrderEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderEvent> kafkaListenerContainerFactory(
            ConsumerFactory<String, OrderEvent> consumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, OrderEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);

        // ✅ Use the proper error handler for Spring Kafka 3.x
        factory.setCommonErrorHandler(errorHandler());

        return factory;
    }

    @Bean
    public DefaultErrorHandler errorHandler() {
        // Retry = 0 times, with 0ms delay → skip problematic messages immediately
        FixedBackOff fixedBackOff = new FixedBackOff(0L, 0);

        DefaultErrorHandler errorHandler = new DefaultErrorHandler((record, exception) -> {
            System.err.println("❌ Kafka Consumer Error:");
            System.err.println("  Record: " + record);
            System.err.println("  Exception: " + exception.getMessage());
        }, fixedBackOff);

        // Optionally mark some exceptions as non-retryable
        errorHandler.addNotRetryableExceptions(SerializationException.class);

        return errorHandler;
    }

    @Bean
    public ConsumerFactory<String, OrderEvent> consumerFactory() {
        JsonDeserializer<OrderEvent> deserializer = new JsonDeserializer<>(OrderEvent.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("com.example.order_service.dto");
        deserializer.setUseTypeMapperForKey(true);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "order-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }
}
