package com.youyk.anchoreerchat.repository.chat;

import com.youyk.anchoreerchat.dto.chat.ChatMessageDto;
import com.youyk.anchoreerchat.dto.chat.ChatRoomDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomChatRoomMemberRepository {
    Slice<ChatMessageDto> findPastChatRoomMessagesByRoomId(final Pageable pageRequest, final Long roomId);
}
