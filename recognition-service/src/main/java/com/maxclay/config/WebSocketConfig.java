package com.maxclay.config;

import com.maxclay.controller.AlertsWebSocketHandler;
import com.maxclay.controller.MessageHandler;
import com.maxclay.controller.TasksWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

/**
 * @author Vlad Glinskiy
 */
@EnableWebSocket
@Configuration
public class WebSocketConfig implements WebSocketConfigurer {

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(1048567);
        return container;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(messageHandler(), "/images");
        registry.addHandler(tasksHandler(), "/ws-tasks").setAllowedOrigins("*");
        registry.addHandler(alertsHandler(), "/ws-alerts").setAllowedOrigins("*");
    }

    @Bean
    public MessageHandler messageHandler() {
        return new MessageHandler();
    }

    @Bean
    public TasksWebSocketHandler tasksHandler() {
        return new TasksWebSocketHandler();
    }

    @Bean
    public AlertsWebSocketHandler alertsHandler() {
        return new AlertsWebSocketHandler();
    }
}
