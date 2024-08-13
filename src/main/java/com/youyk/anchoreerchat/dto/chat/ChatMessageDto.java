package com.youyk.anchoreerchat.dto.chat;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ChatMessageDto(
        String content,
        Long senderId,
        String senderName,
        Long chatRoomId,
        String chatRoomName,
        LocalDateTime createdAt
) implements Serializable {
    @QueryProjection
    public ChatMessageDto(String content, Long senderId, String senderName, Long chatRoomId, String chatRoomName, LocalDateTime createdAt) {
        this.content = content;
        this.senderId = senderId;
        this.senderName = senderName;
        this.chatRoomId = chatRoomId;
        this.chatRoomName = chatRoomName;
        this.createdAt = createdAt;
    }
}
