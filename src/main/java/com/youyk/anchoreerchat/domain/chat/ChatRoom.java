package com.youyk.anchoreerchat.domain.chat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ChatRoom {
    @GeneratedValue
    @Id
    private Long roomId;
    private String roomName;
}
