package com.youyk.anchoreerchat.service.chat;

import com.youyk.anchoreerchat.common.response.DataResponse;
import com.youyk.anchoreerchat.dto.chat.ChatRoomDto;
import com.youyk.anchoreerchat.entity.chat.ChatRoom;
import com.youyk.anchoreerchat.entity.participant.Participant;
import com.youyk.anchoreerchat.repository.chat.ChatRoomRepository;
import com.youyk.anchoreerchat.repository.participant.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ParticipantRepository participantRepository;
    @Transactional
    @Override
    public void createChatRoom(final String roomName, final List<Long> memberIds) {
        //채팅방 생성
        final ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.builder().roomName(roomName).build());


        final List<Participant> participants = memberIds.stream()
                .map(m -> Participant.builder()
                        .chatRoomId(chatRoom.getRoomId())
                        .memberId(m)
                        .joinedDateTime(LocalDateTime.now())
                        .build())
                .toList();
        //채팅방 참여자 생성
        participantRepository.saveAll(participants);
    }

    @Override
    public DataResponse<List<ChatRoomDto>> getChatRoomByRecentLoginMember() {
        //현재 시간으로부터 30분 전
        final LocalDateTime thirtyMinutesAgo = LocalDateTime.now().minusMinutes(30);
        //30분 내에 접속한 사용자 수의 내림차순으로 채팅 목록 정렬
        return DataResponse.from(chatRoomRepository.findChatRoomByMemberLoginDateTimeOrderByMemberCountDesc(thirtyMinutesAgo));
    }
}
