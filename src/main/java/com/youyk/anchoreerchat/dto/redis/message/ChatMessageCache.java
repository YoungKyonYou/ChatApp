package com.youyk.anchoreerchat.dto.redis.message;

import com.youyk.anchoreerchat.dto.chat.ChatMessageDto;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public record ChatMessageCache(
        String content,
        Long senderId,
        String senderName,
        Long chatRoomId,
        String chatRoomName,
        LocalDateTime createdAt
) implements Serializable {
    public static List<ChatMessageCache> of(final List<ChatMessageDto> messageDtos) {
        return messageDtos.stream()
                .map((messageDto) -> new ChatMessageCache(
                        messageDto.content(),
                        messageDto.senderId(),
                        messageDto.senderName(),
                        messageDto.chatRoomId(),
                        messageDto.chatRoomName(),
                        messageDto.createdAt()
                ))
                .toList();
    }
}
