package com.maxclay.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxclay.model.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;

/**
 * @author Vlad Glinskiy
 */
public class AlertsWebSocketHandler extends AbstractWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    private WebSocketSession webSocketSession;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.info("Received text message: {}", message.getPayload());
    }

    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
        logger.info("Received binary message");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("Connection established");
        this.webSocketSession = session;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("Connection closed. Status: {}", status);
    }

    public void sendAlert(Alert alert) {

        if (this.webSocketSession == null || !this.webSocketSession.isOpen()) {
            return;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            TextMessage textMessage = new TextMessage(mapper.writeValueAsString(alert));
            webSocketSession.sendMessage(textMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
