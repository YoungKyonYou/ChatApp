package com.youyk.anchoreerchat.domain.chat;


import com.youyk.anchoreerchat.domain.message.constant.MessageType;

public record ChatMessage(
        String content,
        String sender,
        MessageType type
) {
}
