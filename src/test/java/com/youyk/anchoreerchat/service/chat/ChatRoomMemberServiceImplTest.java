package com.youyk.anchoreerchat.service.chat;


import com.youyk.anchoreerchat.dto.chat.ChatMessageDto;
import com.youyk.anchoreerchat.entity.chat.ChatRoom;
import com.youyk.anchoreerchat.repository.chat.ChatRoomRepository;
import com.youyk.anchoreerchat.request.page.PageableRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Slice;

import java.util.List;

@SpringBootTest
class ChatRoomMemberServiceImplTest {
    @Autowired
    private ChatRoomMemberService chatRoomMemberService;
    @Autowired
    private ChatRoomRepository chatRoomRepository;


    @Test
    void 채팅방의_과거_메시지를_10개씩_정상적으로_불러옵니다() {
        //when
        ChatRoom chatRoom = chatRoomRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
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