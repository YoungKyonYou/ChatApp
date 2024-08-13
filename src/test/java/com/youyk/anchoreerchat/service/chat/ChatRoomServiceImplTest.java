package com.youyk.anchoreerchat.service.chat;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.youyk.anchoreerchat.entity.chat.ChatRoom;
import com.youyk.anchoreerchat.entity.participant.Participant;
import com.youyk.anchoreerchat.repository.chat.ChatRoomRepository;
import com.youyk.anchoreerchat.repository.participant.ParticipantRepository;
import com.youyk.anchoreerchat.util.InitializeSpringBootTest;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


@InitializeSpringBootTest
public class ChatRoomServiceImplTest {
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;


    @Test
    void 채팅방이_정상적으로_만들어집니다() {
        //given
        final String roomName = "BlahBlahBlah";
        final List<Long> memberIds = Arrays.asList(1L, 2L, 3L);

        //when
        chatRoomService.createChatRoom(roomName, memberIds);

        //then
        final ChatRoom chatRoom = chatRoomRepository.findAll().get(0);
        final List<Participant> participants = participantRepository.findAll();

        Assertions.assertAll(
                () -> assertEquals(1, chatRoom.getRoomId()),
                () -> assertEquals(roomName, chatRoom.getRoomName()),
                () -> assertEquals(3, participants.size()),
                () -> assertArrayEquals(
                        memberIds.toArray(),
                        participants.stream().map(Participant::getMemberId).toArray())
        );

    }
}