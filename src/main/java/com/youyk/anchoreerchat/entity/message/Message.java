package com.youyk.anchoreerchat.entity.message;

import com.youyk.anchoreerchat.entity.chat.ChatMessage;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Message {
    @GeneratedValue
    @Id
    private Long messageId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Message from(final ChatMessage chatMessage) {
        return Message.builder()
                .content(chatMessage.content())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
