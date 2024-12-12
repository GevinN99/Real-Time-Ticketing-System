package com.example.ticketsystem.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class TicketStatusHandler extends TextWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(TicketStatusHandler.class); // Logger for logging events
    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>(); // List to store active WebSocket sessions

    // Method called when a new WebSocket connection is established
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session); // Add the new session to the list
    }

    // Method called when a WebSocket connection is closed
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session); // Remove the closed session from the list
    }

    // Method to handle incoming text messages (not used in this implementation)
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    }

    // Method to send a ticket status update to all active WebSocket sessions
    public void sendTicketStatusUpdate(String message) {
        TextMessage textMessage = new TextMessage(message); // Create a new text message with the provided content
        for (WebSocketSession session : sessions) { // Iterate through all active sessions
            try {
                if (session.isOpen()) { // Check if the session is open
                    session.sendMessage(textMessage); // Send the message to the session
                }
            } catch (IOException e) {
                logger.error("Error sending message to session: " + session.getId(), e); // Log any errors that occur
            }
        }
    }
}