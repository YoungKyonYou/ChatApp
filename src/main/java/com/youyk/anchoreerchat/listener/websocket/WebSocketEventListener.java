package com.youyk.anchoreerchat.listener.websocket;

import com.youyk.anchoreerchat.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@RequiredArgsConstructor
@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
    private final SimpMessageSendingOperations messagingTemplate;
    private final MemberService memberService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

/*    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        Long memberId = (Long) headerAccessor.getSessionAttributes().get("memberId");
        Member member = memberService.findById(memberId);
        if(Objects.nonNull(member)) {
            ChatMessage chatMessage = new ChatMessage("USER LEAVED", member, MessageType.LEAVE);

            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }*/
}
