package com.youyk.anchoreerchat.repository.chat;

import com.youyk.anchoreerchat.dto.chat.ChatRoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomChatRoomRepository {
    List<ChatRoomDto> findChatRoomByMemberLoginDateTimeOrderByMemberCountDesc(LocalDateTime thirtyMinutesAgo);
}
