package com.youyk.anchoreerchat.entity.chat;

import com.youyk.anchoreerchat.entity.message.Message;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "CHAT_ROOM_MEMBER")
@Entity
public class ChatRoomMember {
    @GeneratedValue
    @Id
    private Long chatRoomMemberId;

    private Long senderId;

    private Long chatRoomId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id")
    private Message message;

    private LocalDateTime createdAt;

    public static ChatRoomMember of(final Long memberId, final Long chatRoomId, final Message message) {
        return ChatRoomMember.builder()
                .senderId(memberId)
                .chatRoomId(chatRoomId)
                .message(message)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
