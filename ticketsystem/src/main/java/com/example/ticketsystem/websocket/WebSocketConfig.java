package com.example.ticketsystem.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final TicketStatusHandler ticketStatusHandler; // Handler to manage WebSocket connections and messages

    // Constructor to initialize the WebSocketConfig with the TicketStatusHandler
    public WebSocketConfig(TicketStatusHandler ticketStatusHandler) {
        this.ticketStatusHandler = ticketStatusHandler;
    }

    // Method to register WebSocket handlers
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(ticketStatusHandler, "/ticket-status") // Register the handler for the "/ticket-status" endpoint
                .setAllowedOrigins("http://localhost:5173"); // Allow connections from the specified origin
    }
}