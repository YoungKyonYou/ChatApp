package com.youyk.anchoreerchat.service.participant;

import com.youyk.anchoreerchat.common.response.DataResponse;
import com.youyk.anchoreerchat.dto.chat.ChatRoomDto;

import java.util.List;

public interface ParticipantService {
    DataResponse<List<ChatRoomDto>> getChatRoomByRecentLoginMember();
}
