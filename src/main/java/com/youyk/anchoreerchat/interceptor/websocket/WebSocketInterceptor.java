package com.youyk.anchoreerchat.interceptor.websocket;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.youyk.anchoreerchat.common.error.exception.DomainExceptionCode;
import com.youyk.anchoreerchat.entity.chat.ChatMessage;
import com.youyk.anchoreerchat.service.participant.ParticipantService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketInterceptor implements ChannelInterceptor {

    private final ParticipantService participantService;
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        //구독시 - 채팅방 생성
        if (SimpMessageType.SUBSCRIBE.equals(accessor.getMessageType())) {
            final String destination = accessor.getDestination();
            final Long roomId = Long.valueOf(destination.replaceAll("[^\\d]", ""));

            // 채팅방 구독시 캐시에 특정 채팅방의 사용자 추가
            participantService.addParticipantInCache(roomId);
        }
        //구독 해제시 - 채팅방 삭제
        if(SimpMessageType.UNSUBSCRIBE.equals(accessor.getMessageType())){
            final String destination = accessor.getDestination();
            final Long roomId = Long.valueOf(destination.replaceAll("[^\\d]", ""));

            //채팅방 구독 해제시 캐시에 특정 채팅방의 사용자 삭제
            participantService.removeParticipantInCache(roomId);
        }
        //메시지 송신시
        if(SimpMessageType.MESSAGE.equals(accessor.getMessageType())){
            final String payload = new String((byte[]) message.getPayload());

            final JsonObject jsonObject = JsonParser.parseString(payload).getAsJsonObject();

            // 특정 값 추출
            final Long senderId = jsonObject.get("senderId").getAsLong();
            final Long chatRoomId = jsonObject.get("chatRoomId").getAsLong();

            //채팅방에 속한 사용자가 아닌 경우 예외처리
            if(!participantService.isParticipant(chatRoomId, senderId)){
                log.error("Not a participant");
                throw DomainExceptionCode.PARTICIPANT_NOT_IN_THE_CHATROOM.newInstance("room id:"+chatRoomId + ", sender id:"+senderId);
            }
        }

        return message;
    }
}
