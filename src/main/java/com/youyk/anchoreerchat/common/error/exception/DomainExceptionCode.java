package com.youyk.anchoreerchat.common.error.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DomainExceptionCode {
    MEMBER_NOT_FOUND("MEMBER NOT FOUND", "존재하지 않는 사용자입니다. [%s]"),
    CHAT_ROOM_NOT_FOUND("CHAT ROOM NOT FOUND", "채팅방이 존재하지 않습니다. [%s]"),
    PARTICIPANT_NOT_FOUND("PARTICIPANT NOT FOUND", "참여자가 존재하지 않습니다. 채팅방 ID : [%s]"),
    PARTICIPANT_NOT_IN_THE_CHATROOM("PARTICIPANT NOT IN THE CHATROOM", "참여자가 채팅방에 존재하지 않습니다. 채팅방 ID : [%s]");
    private final String code;
    private final String message;

    public DomainException newInstance(final Object arg) {
        return new DomainException(code, String.format(message, arg));
    }

}
