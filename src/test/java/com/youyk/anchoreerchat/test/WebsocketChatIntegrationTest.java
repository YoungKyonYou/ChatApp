package com.youyk.anchoreerchat.test;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.youyk.anchoreerchat.entity.chat.ChatRoom;
import com.youyk.anchoreerchat.entity.member.Member;
import com.youyk.anchoreerchat.entity.chat.ChatMessage;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

// 포트번호가 랜덤이 되며, @LocalServerPort를 통해 포트번호를 불러올수 있다.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebSocketChatIntegrationTest {
    @LocalServerPort
    private int port;


    private StompSession stompSession;



    @BeforeEach
    void setup() throws Exception {

        WebSocketStompClient stompClient =
                new WebSocketStompClient(
                        new SockJsClient(List.of(
                                new WebSocketTransport(
                                        new StandardWebSocketClient()
                                )
                        )
                        )
                );

        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        ObjectMapper objectMapper = messageConverter.getObjectMapper();
        objectMapper.registerModules(new JavaTimeModule(), new ParameterNamesModule());
        stompClient.setMessageConverter(messageConverter);
        String url = String.format("ws://localhost:%d/ws-chat", port);
        StompHeaders stompHeaders = new StompHeaders();

        stompSession = stompClient.connect(url, new WebSocketHttpHeaders(), stompHeaders, new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                System.out.println("Connected to WebSocket server");
            }

            @Override
            public void handleTransportError(StompSession session, Throwable exception) {
                System.err.println("WebSocket transport error: " + exception.getMessage());
            }
        }).get(2, TimeUnit.SECONDS);


        CompletableFuture<ChatMessage> subscribeFuture = new CompletableFuture<>();
        Member member = Member.builder()
                .name("Young")
                .loginDateTime(LocalDateTime.now())
                .build();

        ChatRoom chatroom = ChatRoom.builder()
                .roomName("room1")
                .build();

        ChatMessage message = new ChatMessage("Hello", 1L, 1L, LocalDateTime.now());

        stompSession.send("/app/chat/room1/sendMessage", message);

        stompSession.subscribe("/topic/room1", new StompFrameHandler() {
            // 여기서 지정한 타입으로 payload를 역직렬화한다.
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return ChatMessage.class;
            }

            // 여기서 메시지를 가져올 수 있다.
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                subscribeFuture.complete((ChatMessage) payload);
            }
        });

        ChatMessage sub = subscribeFuture.get(3, TimeUnit.SECONDS);
        System.out.println("sub = " + sub);
    }
    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }

    @Test
    void testSendMessage() throws Exception {

        CompletableFuture<ChatMessage> subscribeFuture = new CompletableFuture<>();


        ChatMessage message = new ChatMessage("Hello", 1L, 1L, LocalDateTime.now());

        stompSession.send("/app/chat/room1/sendMessage", message);

        stompSession.subscribe("/topic/room1", new StompFrameHandler() {
            // 여기서 지정한 타입으로 payload를 역직렬화한다.
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return ChatMessage.class;
            }

            // 여기서 메시지를 가져올 수 있다.
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                subscribeFuture.complete((ChatMessage) payload);
            }
        });

        ChatMessage receivedMessage = subscribeFuture.get(3, TimeUnit.SECONDS);

        Assertions.assertThat(receivedMessage).isNotNull();
        Assertions.assertThat(receivedMessage.content()).isEqualTo("Hello");
       // Assertions.assertThat(receivedMessage.sender()).isEqualTo("user1");
    }

/*    @Test
    void testAddUser() throws Exception {
        ChatMessage message = new ChatMessage("user1 has joined", "user1", MessageType.JOIN);

        stompSession.send("/app/chat/room1/addUser", message);

        ChatMessage receivedMessage = blockingQueue.poll(3, TimeUnit.SECONDS);
        Assertions.assertThat(receivedMessage).isNotNull();
        Assertions.assertThat(receivedMessage.content()).isEqualTo("user1 has joined");
        Assertions.assertThat(receivedMessage.sender()).isEqualTo("user1");
        Assertions.assertThat(receivedMessage.type()).isEqualTo(MessageType.JOIN);
    }*/
}
