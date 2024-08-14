package com.youyk.anchoreerchat.repository.chat;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.youyk.anchoreerchat.dto.chat.ChatRoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.youyk.anchoreerchat.entity.chat.QChatRoom.chatRoom;
import static com.youyk.anchoreerchat.entity.member.QMember.member;
import static com.youyk.anchoreerchat.entity.participant.QParticipant.participant;

@RequiredArgsConstructor
@Repository
public class CustomChatRoomRepositoryImpl implements CustomChatRoomRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ChatRoomDto> findChatRoomByMemberLoginDateTimeOrderByMemberCountDesc(LocalDateTime thirtyMinutesAgo) {
        return queryFactory
                .select(
                        Projections.constructor(
                                ChatRoomDto.class,
                                chatRoom.roomId,
                                chatRoom.roomName
                        ))
                .from(chatRoom)
                .leftJoin(participant).on(chatRoom.roomId.eq(participant.chatRoomId))
                .leftJoin(member).on(participant.memberId.eq(member.memberId), member.loginDateTime.goe(thirtyMinutesAgo))
                .groupBy(chatRoom.roomId)
                .orderBy(member.count().desc(), chatRoom.roomId.asc())
                .fetch();
    }
}
