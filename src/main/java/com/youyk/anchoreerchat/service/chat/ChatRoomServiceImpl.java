package com.youyk.anchoreerchat.service.chat;

import com.youyk.anchoreerchat.common.error.exception.DomainExceptionCode;
import com.youyk.anchoreerchat.common.response.DataResponse;
import com.youyk.anchoreerchat.dto.chat.ChatRoomDto;
import com.youyk.anchoreerchat.entity.chat.ChatRoom;
import com.youyk.anchoreerchat.entity.participant.Participant;
import com.youyk.anchoreerchat.repository.chat.ChatRoomRepository;
import com.youyk.anchoreerchat.repository.participant.ParticipantRepository;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ParticipantRepository participantRepository;
    private final Clock clock;
    @Async
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
        final LocalDateTime thirtyMinutesAgo = LocalDateTime.now(clock).minusMinutes(30);
        //30분 내에 접속한 사용자 수의 내림차순으로 채팅 목록 정렬
        final List<ChatRoomDto> chatRooms = chatRoomRepository.findChatRoomByMemberLoginDateTimeOrderByMemberCountDesc(
                thirtyMinutesAgo);

        if(chatRooms.isEmpty()){
            throw DomainExceptionCode.CHAT_ROOM_NOT_FOUND.newInstance("30분 내에 접속한 사용자가 없습니다.");
        }

        return DataResponse.from(chatRooms);
    }

    @Override
    public DataResponse<List<Participant>> getChatRoomParticipants(final Long roomId) {
        final List<Participant> participants = participantRepository.findParticipantsByChatRoomId(roomId);
        if(participants.isEmpty()){
            throw DomainExceptionCode.PARTICIPANT_NOT_FOUND.newInstance(roomId);
        }
        return DataResponse.from(participants);
    }

}
