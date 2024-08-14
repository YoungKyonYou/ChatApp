package com.youyk.anchoreerchat.entity.message;

import com.youyk.anchoreerchat.entity.chat.ChatMessage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "MESSAGE")
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
