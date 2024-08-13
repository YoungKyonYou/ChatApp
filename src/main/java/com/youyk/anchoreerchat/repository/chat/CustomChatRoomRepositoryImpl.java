package com.youyk.anchoreerchat.repository.chat;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.youyk.anchoreerchat.dto.chat.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.youyk.anchoreerchat.entity.chat.QChatRoom.chatRoom;
import static com.youyk.anchoreerchat.entity.chat.QChatRoomMember.chatRoomMember;
import static com.youyk.anchoreerchat.entity.member.QMember.member;
import static com.youyk.anchoreerchat.entity.message.QMessage.message;

@RequiredArgsConstructor
@Repository
public class CustomChatRoomRepositoryImpl implements CustomChatRoomRepository{
    private final JPAQueryFactory queryFactory;
    @Override
    public Slice<ChatMessageDto> findPastChatRoomMessagesByRoomId(final Pageable pageRequest, final Long roomId) {
        final List<ChatMessageDto> pastMessages = queryFactory.select(
                        Projections.constructor(
                                ChatMessageDto.class,
                                message.content,
                                member.memberId,
                                member.name,
                                chatRoom.roomId,
                                chatRoom.roomName,
                                message.createdAt
                        )
                )
                .from(chatRoomMember)
                .leftJoin(member).on(chatRoomMember.senderId.eq(member.memberId))
                .leftJoin(chatRoom).on(chatRoomMember.chatRoomId.eq(chatRoom.roomId))
                .innerJoin(message).on(chatRoomMember.message.messageId.eq(message.messageId))
                .orderBy(message.createdAt.desc())
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        final Long total = queryFactory.select(chatRoomMember.count())
                .from(chatRoomMember)
                .leftJoin(member).on(chatRoomMember.senderId.eq(member.memberId))
                .leftJoin(chatRoom).on(chatRoomMember.chatRoomId.eq(chatRoom.roomId))
                .innerJoin(message).on(chatRoomMember.message.messageId.eq(message.messageId))
                .where(chatRoomMember.chatRoomId.eq(roomId))
                .fetchOne();

        final boolean hasNext = total != null && (pageRequest.getOffset() + pastMessages.size()) < total ;

        return new SliceImpl<>(pastMessages, pageRequest, hasNext);
    }
}
