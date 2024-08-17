package com.youyk.anchoreerchat.service.kafka;

import com.youyk.anchoreerchat.entity.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatMessageConsumer {
    private final ApplicationEventPublisher publisher;
    private final SimpMessagingTemplate messagingTemplate;
    @KafkaListener(topics = "chat-room-topic", groupId = "chat-group", containerFactory = "kafkaMessageListenerContainerFactory")
    public void listen(ChatMessage message) {
        //메시지 비동기 저장
        publisher.publishEvent(message);
        //웹소켓 구독자에게 메시지 전송
        messagingTemplate.convertAndSend("/topic/" + message.chatRoomId(), message);
    }
}
