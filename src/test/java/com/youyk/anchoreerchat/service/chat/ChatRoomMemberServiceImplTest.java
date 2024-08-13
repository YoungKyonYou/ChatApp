package com.youyk.anchoreerchat.service.chat;


import com.youyk.anchoreerchat.dto.chat.ChatMessageDto;
import com.youyk.anchoreerchat.entity.chat.ChatRoom;
import com.youyk.anchoreerchat.entity.chat.ChatRoomMember;
import com.youyk.anchoreerchat.entity.member.Member;
import com.youyk.anchoreerchat.entity.message.Message;
import com.youyk.anchoreerchat.entity.participant.Participant;
import com.youyk.anchoreerchat.repository.chat.ChatRoomMemberRepository;
import com.youyk.anchoreerchat.repository.chat.ChatRoomRepository;
import com.youyk.anchoreerchat.repository.member.MemberRepository;
import com.youyk.anchoreerchat.repository.message.MessageRepository;
import com.youyk.anchoreerchat.repository.participant.ParticipantRepository;
import com.youyk.anchoreerchat.request.page.PageableRequest;
import com.youyk.anchoreerchat.util.InitializeSpringBootTest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;

@InitializeSpringBootTest
class ChatRoomMemberServiceImplTest {
    @Autowired
    private ChatRoomMemberService chatRoomMemberService;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private ChatRoomMemberRepository chatRoomMemberRepository;


    void setup() {
        List<ChatRoom> all = chatRoomRepository.findAll();
        List<Member> all1 = memberRepository.findAll();
        List<Member> all2 = memberRepository.findAll();
        List<ChatRoomMember> all3 = chatRoomMemberRepository.findAll();
        List<Participant> all4 = participantRepository.findAll();

        //given
        Member member1 = Member.builder().name("Sam").loginDateTime(LocalDateTime.now()).build();
        Member member2 = Member.builder().name("Bob").loginDateTime(LocalDateTime.now()).build();
        memberRepository.saveAll(List.of(member1, member2));

        ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.builder().roomName("BlahBlahBlah").build());

        Participant participant = Participant.builder().chatRoomId(chatRoom.getRoomId())
                .memberId(member1.getMemberId())
                .joinedDateTime(LocalDateTime.now()).build();

        participantRepository.save(participant);

        List<Message> messages = new ArrayList<>();
        IntStream.range(0, 100).forEach(i -> {
            messages.add(
                    Message.builder()
                            .content("Hello " + i)
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build()
            );
        });
        messageRepository.saveAll(messages);

        List<ChatRoomMember> chatRoomMembers = new ArrayList<>();
        IntStream.range(0, 50).forEach(i -> {
            chatRoomMembers.add(
                    ChatRoomMember.builder()
                            .senderId(member1.getMemberId())
                            .message(messages.get(i))
                            .chatRoomId(chatRoom.getRoomId())
                            .createdAt(LocalDateTime.now().minusMinutes(i))
                            .build()
            );

            chatRoomMembers.add(
                    ChatRoomMember.builder()
                            .senderId(member2.getMemberId())
                            .message(messages.get(50 + i))
                            .chatRoomId(chatRoom.getRoomId())
                            .createdAt(LocalDateTime.now().minusMinutes(i))
                            .build()
            );

        });

        chatRoomMemberRepository.saveAll(chatRoomMembers);
    }

    @Test
    void 채팅방의_과거_메시지를_10개씩_정상적으로_불러옵니다() {
        setup();
        //when
        List<ChatRoom> all = chatRoomRepository.findAll();
        ChatRoom chatRoom = all.get(0);
        PageableRequest pageRequestFirst = PageableRequest.builder().page(0).size(10).build();
        PageableRequest pageRequestSecond = PageableRequest.builder().page(1).size(10).build();

        Slice<ChatMessageDto> chatRoomMessagesFirst = chatRoomMemberService.getChatRoomMessages(pageRequestFirst,
                chatRoom.getRoomId()).data();
        Slice<ChatMessageDto> chatRoomMessagesSecond = chatRoomMemberService.getChatRoomMessages(pageRequestSecond,
                chatRoom.getRoomId()).data();

        List<ChatMessageDto> contentFirst = chatRoomMessagesFirst.getContent();
        boolean hasNextFirst = chatRoomMessagesFirst.hasNext();

        List<ChatMessageDto> contentSecond = chatRoomMessagesSecond.getContent();
        boolean hasNextSecond = chatRoomMessagesFirst.hasNext();

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(10, contentFirst.size()),
                () -> Assertions.assertTrue(hasNextFirst),
                () -> Assertions.assertEquals(10, contentSecond.size()),
                () -> Assertions.assertTrue(hasNextSecond)

        );
    }
}