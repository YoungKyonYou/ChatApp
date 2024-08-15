package com.youyk.anchoreerchat.common.error.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum DomainExceptionCode {
    MEMBER_NOT_FOUND("MEMBER NOT FOUND", "존재하지 않는 사용자입니다. [%s]", HttpStatus.BAD_REQUEST),
    CHAT_ROOM_NOT_FOUND("CHAT ROOM NOT FOUND", "채팅방이 존재하지 않습니다. [%s]", HttpStatus.BAD_REQUEST),
    PARTICIPANT_NOT_FOUND("PARTICIPANT NOT FOUND", "참여자가 존재하지 않습니다. 채팅방 ID : [%s]", HttpStatus.BAD_REQUEST),;
    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    public DomainException newInstance(final Object arg){
        return new DomainException(code, String.format(message, arg), httpStatus);
    }

}
