package com.youyk.anchoreerchat.repository.chat;

import com.youyk.anchoreerchat.config.QueryDSLConfig;
import com.youyk.anchoreerchat.dto.chat.ChatRoomDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Import(QueryDSLConfig.class)
@DataJpaTest
public class ChatRoomRepositoryTest {
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Test
    void 채팅방_목록을_최근_로그인한_사용자에_따라_정상적으로_가져옵니다(){
        //given
        LocalDateTime thirtyMinutesAgo = LocalDateTime.now().minusMinutes(30);
        //when
        List<ChatRoomDto> chatRoomByMemberLoginDateTimeOrderByMemberCountDesc = chatRoomRepository.findChatRoomByMemberLoginDateTimeOrderByMemberCountDesc(thirtyMinutesAgo);
        List<Long> chatRoomIds = chatRoomByMemberLoginDateTimeOrderByMemberCountDesc.stream().map(ChatRoomDto::roomId).toList();
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(3, chatRoomByMemberLoginDateTimeOrderByMemberCountDesc.size()),
                () -> Assertions.assertIterableEquals(List.of(3L,2L,1L), chatRoomIds)
        );
    }
}
