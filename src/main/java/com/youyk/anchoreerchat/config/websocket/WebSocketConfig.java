package com.youyk.anchoreerchat.config.websocket;

import com.youyk.anchoreerchat.interceptor.websocket.WebSocketInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final WebSocketInterceptor interceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // topic과 /queue로 시작하는 대상에 대한 메시지를 처리하도록 설정
        // 주로 서버에서 클라이언트로 메시지를 전송할 때 사용
        config.enableSimpleBroker("/topic", "/queue");

        //클라이언트에서 서버로 메시지를 전송할 때 사용되는 대상의 접두사를 설정
        // 클라이언트 에서는 /app/{@MessageMapping} value으로 메시지를 전송
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //web socket connection이 최초로 이루어지는 곳(handshake)
        registry.addEndpoint("/ws-chat").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(interceptor);
    }

}
