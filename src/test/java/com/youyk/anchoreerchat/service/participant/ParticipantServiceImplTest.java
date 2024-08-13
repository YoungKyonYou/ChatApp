package com.youyk.anchoreerchat.service.participant;

import static org.assertj.core.api.Assertions.assertThat;

import com.youyk.anchoreerchat.dto.chat.ChatRoomDto;
import com.youyk.anchoreerchat.entity.chat.ChatRoom;
import com.youyk.anchoreerchat.entity.chat.ChatRoomMember;
import com.youyk.anchoreerchat.entity.member.Member;
import com.youyk.anchoreerchat.entity.message.Message;
import com.youyk.anchoreerchat.entity.participant.Participant;
import com.youyk.anchoreerchat.repository.chat.ChatRoomRepository;
import com.youyk.anchoreerchat.repository.member.MemberRepository;
import com.youyk.anchoreerchat.repository.participant.ParticipantRepository;
import com.youyk.anchoreerchat.util.InitializeSpringBootTest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@InitializeSpringBootTest
class ParticipantServiceImplTest {
    @Autowired
    private ParticipantService participantService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private ParticipantRepository participantRepository;

    void setup() {
        //각 채팅방의 사용자 수
        Integer[] memberCountPerChatRoom = {4, 4, 4, 4};
        Integer[] minutesAgo = {10, 40, 50, 60, 10, 20, 40, 50, 0, 10, 20, 40, 0, 10, 15, 20};
       // Long[] roomIds = {1L, 2L, 3L, 4L};
        int minutesAgoIdx = 0;
        int memberId = 0;

        List<Member> members = new ArrayList<>();
        for (Integer integer : memberCountPerChatRoom) {
            for (int j = 0; j < integer; j++) {
                Member member = Member.builder().name("Sam")
                        .loginDateTime(LocalDateTime.now()
                                .minusMinutes(minutesAgo[minutesAgoIdx++])).build();
                members.add(member);
            }
        }

        memberRepository.saveAll(members);

        List<ChatRoom> chatRooms = new ArrayList<>();
        List<Participant> participants = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            ChatRoom chatRoom = ChatRoom.builder().roomName("BlahBlahBlah").build();
            chatRooms.add(chatRoom);
            for (int j = 0; j < memberCountPerChatRoom[i]; j++) {
                Participant participant = Participant.builder().chatRoomId(chatRoom.getRoomId())
                        .memberId(members.get(memberId++).getMemberId())
                        .joinedDateTime(LocalDateTime.now()).build();
                participants.add(participant);
            }
        }

        chatRoomRepository.saveAll(chatRooms);
        participantRepository.saveAll(participants);
    }

    @Test
    void 채팅목록이_30분_내에_접속한_사용자_수의_내림차순으로_정상적으로_정렬됩니다() {
        setup();

        List<ChatRoomDto> chatRoomDtos = participantService.getChatRoomByRecentLoginMember().data();
        List<Long> roomIds = chatRoomDtos.stream().map(ChatRoomDto::roomId).toList();
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        List<Long> chatRoomIds = chatRooms.stream().map(ChatRoom::getRoomId).collect(Collectors.toList());
        Collections.reverse(chatRoomIds);
        assertThat(roomIds).containsExactly(chatRoomIds.toArray(new Long[0]));
    }
}