package com.youyk.anchoreerchat.service.chat;

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
        final ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.builder().roomName(roomName).build());
        final List<Participant> participants = memberIds.stream()
                .map(m -> Participant.builder()
                        .chatRoomId(chatRoom.getRoomId())
                        .memberId(m)
                        .joinedDateTime(LocalDateTime.now())
                        .build())
                .toList();
        participantRepository.saveAll(participants);
    }
}
