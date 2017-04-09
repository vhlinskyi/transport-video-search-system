package com.maxclay.controller;

import com.maxclay.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;

/**
 * @author Vlad Glinskiy
 */
public class MessageHandler extends AbstractWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    @Autowired
    private SearchService searchService;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.info("Received text message: " + message.getPayload());
    }

    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {

        if (message.getPayload() == null || message.getPayload().array().length == 0) {
            return;
        }

        // TODO return type. Send result via WebSockets
        searchService.recognizeAndSearch(message.getPayload().array());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("Connection established");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("Connection closed. Status: " + status);
    }
}
