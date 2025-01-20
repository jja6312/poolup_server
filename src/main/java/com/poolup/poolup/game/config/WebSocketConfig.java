package com.poolup.poolup.game.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/game-websocket") // 클라이언트 WebSocket 연결 엔드포인트
                .setAllowedOrigins("http://localhost:5173") // CORS 설정
                .withSockJS(); // SockJS 지원
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // 클라이언트가 구독하는 경로
        config.setApplicationDestinationPrefixes("/app"); // 클라이언트가 메시지를 보내는 경로
    }

}
