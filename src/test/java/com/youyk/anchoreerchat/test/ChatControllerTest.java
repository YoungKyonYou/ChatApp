package com.youyk.anchoreerchat.test;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.youyk.anchoreerchat.domain.message.constant.MessageType;
import com.youyk.anchoreerchat.domain.chat.ChatMessage;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

@SpringBootTest
@AutoConfigureMockMvc
public class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private BlockingQueue<ChatMessage> blockingQueue;
    private WebSocketStompClient stompClient;

    @Test
    public void testSendMessage() throws Exception {
        stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        blockingQueue = new LinkedBlockingDeque<>();

        StompSession session = stompClient.connect("ws://localhost:8080/ws-chat", new StompSessionHandlerAdapter() {}).get(1, SECONDS);
        session.subscribe("/topic/1", new DefaultStompFrameHandler());

        ChatMessage chatMessage = new ChatMessage( "testUser", "testMessage", MessageType.CHAT);


        session.send("/app/chat/1/sendMessage", chatMessage);

        ChatMessage received = blockingQueue.poll(1, SECONDS);
        assertEquals(chatMessage, received);
    }

    class DefaultStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders headers) {
            System.out.println(headers.toString());
            return ChatMessage.class;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            blockingQueue.offer((ChatMessage) payload);
        }
    }
    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }
}
