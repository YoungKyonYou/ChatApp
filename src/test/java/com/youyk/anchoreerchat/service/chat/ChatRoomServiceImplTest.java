package com.youyk.anchoreerchat.service.chat;

import com.youyk.anchoreerchat.dto.chat.ChatRoomDto;
import com.youyk.anchoreerchat.entity.chat.ChatRoom;
import com.youyk.anchoreerchat.entity.participant.Participant;
import com.youyk.anchoreerchat.repository.chat.ChatRoomRepository;
import com.youyk.anchoreerchat.repository.participant.ParticipantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ChatRoomServiceImplTest {
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Transactional
    @Test
    void 채팅목록이_30분_내에_접속한_사용자_수의_내림차순으로_정상적으로_정렬됩니다() {
        List<ChatRoomDto> chatRoomDtos = chatRoomService.getChatRoomByRecentLoginMember().data();
        List<Long> roomIds = chatRoomDtos.stream().map(ChatRoomDto::roomId).toList();

        assertThat(roomIds).containsExactly(Arrays.asList(3L, 2L, 1L).toArray(new Long[0]));
    }

    @Transactional
    @Test
    void 채팅방이_정상적으로_만들어집니다() {
        //given
        final String roomName = "BlahBlahBlah";
        final List<Long> memberIds = Arrays.asList(1L, 2L, 3L);

        //when
        chatRoomService.createChatRoom(roomName, memberIds);

        //then
        final ChatRoom chatRoom = chatRoomRepository.findById(4L).orElseThrow(() -> new IllegalArgumentException("채팅방이 생성되지 않았습니다."));
        final List<Participant> participants = participantRepository.findByChatRoomId(4L);

        Assertions.assertAll(
                () -> assertEquals(4, chatRoom.getRoomId()),
                () -> assertEquals(roomName, chatRoom.getRoomName()),
                () -> assertEquals(3, participants.size()),
                () -> assertArrayEquals(
                        memberIds.toArray(),
                        participants.stream().map(Participant::getMemberId).toArray())
        );

    }

}