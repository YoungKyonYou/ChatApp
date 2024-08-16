package com.youyk.anchoreerchat.config.kafka;

import com.youyk.anchoreerchat.entity.chat.ChatMessage;
import io.lettuce.core.api.push.PushMessage;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaConsumerConfig {
    private final String consumerHost;

    public KafkaConsumerConfig(@Value("${spring.kafka.consumer.bootstrap-servers}") final String consumerHost) {
        this.consumerHost = consumerHost;
    }

    private <T> JsonDeserializer<T> getDeserializer(final JsonDeserializer<T> deserializer) {
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);
        return deserializer;
    }

    private <T> Map<String, Object> getProp(final JsonDeserializer<T> deserializer) {
        final Map<String, Object> prop = new HashMap<>();

        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerHost);
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        prop.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        return prop;
    }

    @Bean
    public ConsumerFactory<String, ChatMessage> consumerMessageFactory() {
        final JsonDeserializer<ChatMessage> deserializer = getDeserializer(
                new JsonDeserializer<>(ChatMessage.class));
        final Map<String, Object> prop = getProp(deserializer);

        return new DefaultKafkaConsumerFactory<>(
                prop,
                new StringDeserializer(),
                deserializer);
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ChatMessage> kafkaMessageListenerContainerFactory() {
        final ConcurrentKafkaListenerContainerFactory<String, ChatMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerMessageFactory());
        return factory;
    }



}
