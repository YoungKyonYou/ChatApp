package com.youyk.anchoreerchat.entity.chat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "CHAT_ROOM")
@Entity
public class ChatRoom {
    @GeneratedValue
    @Id
    private Long roomId;
    private String roomName;
}
