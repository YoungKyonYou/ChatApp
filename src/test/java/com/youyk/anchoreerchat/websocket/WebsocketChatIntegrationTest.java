package com.youyk.anchoreerchat.websocket;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.youyk.anchoreerchat.entity.chat.ChatRoom;
import com.youyk.anchoreerchat.entity.member.Member;
import com.youyk.anchoreerchat.entity.chat.ChatMessage;
import com.youyk.anchoreerchat.util.DatabaseCleaner;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

// 포트번호가 랜덤이 되며, @LocalServerPort를 통해 포트번호를 불러올수 있다.
@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebSocketChatIntegrationTest {
    @LocalServerPort
    private int port;
    private StompSession stompSession;
    @Autowired
    DatabaseCleaner databaseCleaner;




    @BeforeEach
    void setup() throws Exception {
        //databaseCleaner.clear();

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

        stompSession = stompClient.connectAsync(url, new WebSocketHttpHeaders(), stompHeaders,
                new StompSessionHandlerAdapter() {
                    @Override
                    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                        System.out.println("Connected to WebSocket server");
                    }

                    @Override
                    public void handleTransportError(StompSession session, Throwable exception) {
                        System.err.println("WebSocket transport error: " + exception.getMessage());
                    }
                }).get(3, TimeUnit.SECONDS);
    }

    @Test
    void 웹소켓을_통해_메시지가_정상적으로_송수신됩니다() throws Exception {

        CompletableFuture<ChatMessage> subscribeFuture = new CompletableFuture<>();


        ChatMessage message = new ChatMessage("Hello", 1L, 1L, LocalDateTime.now());

        stompSession.send("/app/chat/1/send-message", message);

        stompSession.subscribe("/topic/1", new StompFrameHandler() {
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

        stompSession.send("/app/chat/1/send-message", message);

        ChatMessage receivedMessage = subscribeFuture.get(10, TimeUnit.SECONDS);

        Assertions.assertThat(receivedMessage).isNotNull();
        Assertions.assertThat(receivedMessage.content()).isEqualTo("Hello");
    }

}
