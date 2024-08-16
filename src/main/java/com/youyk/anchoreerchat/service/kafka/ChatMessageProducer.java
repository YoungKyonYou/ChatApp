package com.youyk.anchoreerchat.service.kafka;

import com.youyk.anchoreerchat.entity.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatMessageProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    public void sendMessage(String topic, ChatMessage chatMessage) {
        kafkaTemplate.send(topic, chatMessage);
    }
}
