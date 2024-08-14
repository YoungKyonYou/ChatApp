package com.youyk.anchoreerchat.dto.chat;

import com.querydsl.core.annotations.QueryProjection;
import com.youyk.anchoreerchat.entity.chat.ChatRoom;
import lombok.Builder;

import java.util.List;

@Builder
public record ChatRoomDto(
        Long roomId,
        String roomName
) {
    @QueryProjection
    public ChatRoomDto(Long roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }

    public static List<ChatRoomDto> from(List<ChatRoom> chatRooms) {
        return chatRooms.stream()
                .map(chatRoom -> ChatRoomDto.builder()
                        .roomId(chatRoom.getRoomId())
                        .roomName(chatRoom.getRoomName())
                        .build())
                .toList();
    }
}
