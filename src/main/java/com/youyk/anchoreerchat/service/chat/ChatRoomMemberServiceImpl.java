package com.youyk.anchoreerchat.service.chat;

import com.youyk.anchoreerchat.common.response.DataResponse;
import com.youyk.anchoreerchat.dto.chat.ChatMessageDto;
import com.youyk.anchoreerchat.repository.chat.ChatRoomMemberRepository;
import com.youyk.anchoreerchat.request.page.PageableRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChatRoomMemberServiceImpl implements ChatRoomMemberService {
    private final ChatRoomMemberRepository chatRoomMemberRepository;

    @Transactional(readOnly = true)
    @Override
    public DataResponse<Slice<ChatMessageDto>> getChatRoomMessages(final PageableRequest pageRequest, final Long roomId) {
        final PageRequest pageable = PageRequest.of(pageRequest.page(), pageRequest.size());
        final Slice<ChatMessageDto> pastMessagesSlice = chatRoomMemberRepository.findPastChatRoomMessagesByRoomId(pageable, roomId);
        return DataResponse.from(pastMessagesSlice);
    }

}
