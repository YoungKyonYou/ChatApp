package com.youyk.anchoreerchat.dto.chat;

import com.youyk.anchoreerchat.entity.chat.ChatRoom;
import lombok.Builder;

import java.util.List;

@Builder
public record ChatRoomDto(
        Long roomId,
        String roomName
) {
    public static List<ChatRoomDto> from(List<ChatRoom> chatRooms) {
        return chatRooms.stream()
                .map(chatRoom -> ChatRoomDto.builder()
                        .roomId(chatRoom.getRoomId())
                        .roomName(chatRoom.getRoomName())
                        .build())
                .toList();
    }
}
