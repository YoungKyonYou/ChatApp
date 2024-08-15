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
        //슬라이싱을 위한 PageRequest 생성
        final PageRequest pageable = PageRequest.of(pageRequest.page(), pageRequest.size());
        //pageRequest에 해당하는 만큼 과거 채팅 내역 반환
        final Slice<ChatMessageDto> pastMessagesSlice = chatRoomMemberRepository.findPastChatRoomMessagesByRoomId(pageable, roomId);
        return DataResponse.from(pastMessagesSlice);
    }

}
