package com.youyk.anchoreerchat.repository.chat;

import com.youyk.anchoreerchat.dto.chat.ChatMessageDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

public interface CustomChatRoomRepository {
    Slice<ChatMessageDto> findPastChatRoomMessagesByRoomId(final Pageable pageRequest, final Long roomId);
}
