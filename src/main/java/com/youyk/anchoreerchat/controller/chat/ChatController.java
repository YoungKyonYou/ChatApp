package com.youyk.anchoreerchat.controller.chat;

import com.youyk.anchoreerchat.domain.chat.ChatMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    //client 가 /app/chat/{roomId}/sendMessage로 메시지를 보냄
    @MessageMapping("/chat/{roomId}/sendMessage")
    //서버가 /topic/messages로 메시지 전달
    @SendTo("/topic/{roomId}")
    public ChatMessage sendMessage(@DestinationVariable String roomId, @Payload ChatMessage chatMessage) {
        System.out.println("sendMessage called with roomId: " + roomId + " and message: " + chatMessage.content());
        return chatMessage;
    }

    @MessageMapping("/chat/{roomId}/addUser")
    @SendTo("/topic/{roomId}")
    public ChatMessage addUser(@DestinationVariable String roomId, @Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.sender());
        return chatMessage;
    }
}
