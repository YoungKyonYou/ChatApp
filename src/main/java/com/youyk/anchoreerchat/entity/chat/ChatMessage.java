package com.youyk.anchoreerchat.entity.chat;


import java.time.LocalDateTime;

public record ChatMessage(
        String content,
        Long senderId,
        Long chatRoomId,
        LocalDateTime createdAt

) {
}
