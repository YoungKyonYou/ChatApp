package com.youyk.anchoreerchat.service.chat;

import com.youyk.anchoreerchat.common.response.DataResponse;
import com.youyk.anchoreerchat.dto.chat.ChatMessageDto;
import com.youyk.anchoreerchat.entity.message.Message;
import com.youyk.anchoreerchat.repository.chat.ChatRoomMemberRepository;
import com.youyk.anchoreerchat.repository.chat.ChatRoomRepository;
import com.youyk.anchoreerchat.repository.message.MessageRepository;
import com.youyk.anchoreerchat.repository.participant.ParticipantRepository;
import com.youyk.anchoreerchat.request.page.PageableRequest;
import com.youyk.anchoreerchat.service.cache.message.MessageCacheService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChatRoomMemberServiceImpl implements ChatRoomMemberService {
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ParticipantRepository participantRepository;
    private final MessageCacheService messageCacheService;

    private final MessageRepository messageRepository;

    @Transactional(readOnly = true)
    @Override
    public DataResponse<Slice<ChatMessageDto>> getChatRoomMessages(final PageableRequest pageRequest, final Long roomId) {
        //슬라이싱을 위한 PageRequest 생성
        final PageRequest pageable = PageRequest.of(pageRequest.page(), pageRequest.size());

        //캐싱된 메시지가 있다면 캐시된 메시지 반환
        if (messageCacheService.hasCachedMessages(roomId, pageable)) {
            return DataResponse.from(messageCacheService.retrieveMessagesFromCache(roomId, pageable));
        }

        //pageRequest에 해당하는 만큼 과거 채팅 내역 반환
        final Slice<ChatMessageDto> pastMessagesSlice = chatRoomMemberRepository.findPastChatRoomMessagesByRoomId(pageable, roomId);
        //메세지 캐시에 저장
        messageCacheService.cacheSliceMessages(pastMessagesSlice.getContent(), roomId, pastMessagesSlice.hasNext(),
                pageable);
        return DataResponse.from(pastMessagesSlice);
    }


    @Async
    @Override
    public void removeChatRoom(Long roomId) {
        processDeleteChatRoom(roomId);
    }

    @Transactional
    protected void processDeleteChatRoom(Long roomId) {
        final List<Message> messagesByChatRoomId = chatRoomMemberRepository.findMessageByChatRoomId(roomId);
        chatRoomMemberRepository.deleteByChatRoomId(roomId);
        chatRoomRepository.deleteById(roomId);
        participantRepository.deleteByChatRoomId(roomId);
        messageRepository.deleteAllInBatch(messagesByChatRoomId);
    }

}
