package com.youyk.anchoreerchat.controller.chat;

import com.youyk.anchoreerchat.entity.chat.ChatMessage;
import com.youyk.anchoreerchat.service.kafka.ChatMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class WebSocketController {
    private final ChatMessageProducer chatMessageProducer;

    @MessageMapping("/chat/{roomId}/send-message")
    public void sendMessage(@DestinationVariable("roomId") String roomId, @Payload ChatMessage chatMessage) {
        chatMessageProducer.sendMessage("chat-room-topic", chatMessage);
    }
}
