package com.youyk.anchoreerchat.service.chat;

import com.youyk.anchoreerchat.common.response.DataResponse;
import com.youyk.anchoreerchat.dto.chat.ChatRoomDto;

import java.time.Clock;
import java.util.List;

public interface ChatRoomService {
    void createChatRoom(final String roomName, final List<Long> memberIds);
    DataResponse<List<ChatRoomDto>> getChatRoomByRecentLoginMember();

    void removeChatRoom(final Long roomId);
}
