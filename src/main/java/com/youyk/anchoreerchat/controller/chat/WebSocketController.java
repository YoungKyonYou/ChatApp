package com.youyk.anchoreerchat.controller.chat;

import com.youyk.anchoreerchat.entity.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class WebSocketController {
    private final ApplicationEventPublisher publisher;

    //client 가 /app/chat/{roomId}/sendMessage로 메시지를 보냄
    @MessageMapping("/chat/{roomId}/sendMessage")
    //서버가 /topic/messages로 메시지 전달
    @SendTo("/topic/{roomId}")
    public ChatMessage sendMessage(@DestinationVariable("roomId") String roomId, @Payload ChatMessage chatMessage) {
        //EventPublisher를 통해 ChatMessage를 비동기로 저장
        publisher.publishEvent(chatMessage);
        return chatMessage;
    }
}
