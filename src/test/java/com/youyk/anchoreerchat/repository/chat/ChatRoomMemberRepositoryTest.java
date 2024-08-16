package com.youyk.anchoreerchat.repository.chat;


import com.youyk.anchoreerchat.config.QueryDSLConfig;
import com.youyk.anchoreerchat.dto.chat.ChatMessageDto;
import com.youyk.anchoreerchat.request.page.PageableRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.util.List;


@Import(QueryDSLConfig.class)
@DataJpaTest
class ChatRoomMemberRepositoryTest {
    @Autowired
    private ChatRoomMemberRepository chatRoomMemberRepository;

    @Test
    void 채팅방의_과거_메시지를_10개씩_정상적으로_불러옵니다() {
        //given
        final PageableRequest pageRequestFirst = PageableRequest.builder().page(0).size(10).build();
        final PageRequest pageable = PageRequest.of(pageRequestFirst.page(), pageRequestFirst.size());

        //when
        Slice<ChatMessageDto> pastChatRoomMessagesByRoomId = chatRoomMemberRepository.findPastChatRoomMessagesByRoomId(pageable, 1L);

        final List<ChatMessageDto> contentFirst = pastChatRoomMessagesByRoomId.getContent();
        final boolean hasNextFirst = pastChatRoomMessagesByRoomId.hasNext();

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(10, contentFirst.size()),
                () -> Assertions.assertTrue(hasNextFirst)
        );
    }


}