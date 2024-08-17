package com.youyk.anchoreerchat.dto.chat;

import com.querydsl.core.annotations.QueryProjection;
import com.youyk.anchoreerchat.dto.redis.message.ChatMessageCache;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Builder
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

    public static List<ChatMessageDto> from(List<ChatMessageCache> cacheMessages){
        return cacheMessages.stream()
                .map((cacheMessage) -> new ChatMessageDto(
                        cacheMessage.content(),
                        cacheMessage.senderId(),
                        cacheMessage.senderName(),
                        cacheMessage.chatRoomId(),
                        cacheMessage.chatRoomName(),
                        cacheMessage.createdAt()
                ))
                .toList();
    }
}
