package com.youyk.anchoreerchat.service.chat;

import com.youyk.anchoreerchat.common.response.DataResponse;

import java.util.List;

public interface ChatRoomService {
    void createChatRoom(final String roomName, final List<Long> memberIds);
}
