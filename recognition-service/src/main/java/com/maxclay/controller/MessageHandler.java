package com.maxclay.controller;

import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Vlad Glinskiy
 */
public class MessageHandler extends AbstractWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.info("Received text message: " + message.getPayload());
    }

    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {

        BufferedImage image = ImageIO.read(new ByteBufferBackedInputStream(message.getPayload()));
        logger.info("Received image. Width: {}, Height: {}", image.getWidth(), image.getHeight());
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
