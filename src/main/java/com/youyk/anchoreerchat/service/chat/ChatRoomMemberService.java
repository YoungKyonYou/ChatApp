package com.youyk.anchoreerchat.service.chat;

import com.youyk.anchoreerchat.common.response.DataResponse;
import com.youyk.anchoreerchat.dto.chat.ChatMessageDto;
import com.youyk.anchoreerchat.request.page.PageableRequest;
import org.springframework.data.domain.Slice;

public interface ChatRoomMemberService {

    DataResponse<Slice<ChatMessageDto>> getChatRoomMessages(final PageableRequest pageRequest, final Long roomId);
}
