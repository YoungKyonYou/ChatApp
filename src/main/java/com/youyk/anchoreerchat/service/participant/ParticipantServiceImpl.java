package com.youyk.anchoreerchat.service.participant;


import com.youyk.anchoreerchat.common.response.DataResponse;
import com.youyk.anchoreerchat.entity.chat.ChatRoom;
import com.youyk.anchoreerchat.dto.chat.ChatRoomDto;
import com.youyk.anchoreerchat.repository.participant.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ParticipantServiceImpl implements ParticipantService{
    private final ParticipantRepository participantRepository;
    @Transactional(readOnly = true)
    @Override
    public DataResponse<List<ChatRoomDto>> getChatRoomByRecentLoginMember() {
        final LocalDateTime thirtyMinutesAgo = LocalDateTime.now().minusMinutes(30);
        final List<ChatRoom> chatRooms = participantRepository.findChatRoomByMemberLoginDateTimeOrderByMemberCountDesc(thirtyMinutesAgo);
        return DataResponse.from(ChatRoomDto.from(chatRooms));
    }
}
