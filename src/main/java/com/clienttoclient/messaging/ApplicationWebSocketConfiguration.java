package com.clienttoclient.messaging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class ApplicationWebSocketConfiguration implements WebSocketConfigurer {

    @Bean
    public WebSocketHandler wsHandler() {
        return new ApplicationWebSocketMessageHandler();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(wsHandler(), "/messenger")
                .setAllowedOrigins("*");
    }
}
