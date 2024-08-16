package com.youyk.anchoreerchat.service.chat;

import com.youyk.anchoreerchat.dto.chat.ChatRoomDto;
import com.youyk.anchoreerchat.entity.chat.ChatRoom;
import com.youyk.anchoreerchat.entity.participant.Participant;
import com.youyk.anchoreerchat.repository.chat.ChatRoomRepository;
import com.youyk.anchoreerchat.repository.participant.ParticipantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatRoomServiceImplTest {
    @InjectMocks
    private ChatRoomServiceImpl chatRoomService;
    @Mock
    private ParticipantRepository participantRepository;
    @Mock
    private ChatRoomRepository chatRoomRepository;

    @Mock
    private Clock clock;


    @Test
    void 채팅목록이_30분_내에_접속한_사용자_수의_내림차순으로_정상적으로_정렬됩니다() {
        // 현재 시간으로부터 30분 전의 시간 생성
        Instant now = Instant.now();
        given(clock.instant()).willReturn(now);
        given(clock.getZone()).willReturn(ZoneId.systemDefault());
        LocalDateTime thirtyMinutesAgo = LocalDateTime.now(clock).minusMinutes(30);


        // Mock된 ChatRoomDto 리스트 생성
        List<ChatRoomDto> mockChatRoomList = Arrays.asList(
                new ChatRoomDto(3L, "Marketing"),
                new ChatRoomDto(2L, "Development"),
                new ChatRoomDto(1L, "General")
        );

        // chatRoomRepository의 동작을 Mocking
        given(chatRoomRepository.findChatRoomByMemberLoginDateTimeOrderByMemberCountDesc(thirtyMinutesAgo))
                .willReturn(mockChatRoomList);

        List<ChatRoomDto> response = chatRoomService.getChatRoomByRecentLoginMember().data();

        // 검증: Repository 메서드가 올바른 인자로 호출되었는지 확인
        verify(chatRoomRepository).findChatRoomByMemberLoginDateTimeOrderByMemberCountDesc(thirtyMinutesAgo);

        // 검증: 반환된 결과가 예상과 일치하는지 확인
        assertThat(response).isEqualTo(mockChatRoomList);
    }

    @Test
    void 채팅방이_정상적으로_만들어집니다() {
        //given
        final String roomName = "BlahBlahBlah";
        final List<Long> memberIds = Arrays.asList(1L, 2L, 3L);
        final ChatRoom mockChatRoom = new ChatRoom(1L, roomName);
        final List<Participant> mockParticipants = Arrays.asList(
                new Participant(1L, 4L, 1L, LocalDateTime.now()),
                new Participant(2L, 4L, 2L, LocalDateTime.now()),
                new Participant(3L, 4L, 3L, LocalDateTime.now())
        );

        //when
        when(chatRoomRepository.save(any(ChatRoom.class))).thenReturn(mockChatRoom);
        when(participantRepository.saveAll(anyList())).thenReturn(mockParticipants);

        chatRoomService.createChatRoom(roomName, memberIds);
        //then
        ArgumentCaptor<ChatRoom> chatRoomCaptor = ArgumentCaptor.forClass(ChatRoom.class);
        verify(chatRoomRepository).save(chatRoomCaptor.capture());
        //생성된 채팅방이 예상대로 저장되었는지 확인
        ChatRoom savedChatRoom = chatRoomCaptor.getValue();
        assertEquals(roomName, savedChatRoom.getRoomName());

        ArgumentCaptor<List<Participant>> participantListCaptor = ArgumentCaptor.forClass(List.class);
        verify(participantRepository).saveAll(participantListCaptor.capture());
        //생성된 참여자들이 예상대로 저장되었는지 확인
        List<Participant> savedParticipants = participantListCaptor.getValue();
        assertEquals(3, savedParticipants.size());
        assertArrayEquals(
                memberIds.toArray(),
                savedParticipants.stream().map(Participant::getMemberId).toArray()
        );
    }

}