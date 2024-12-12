package com.example.ticketsystem.dto;

import java.util.concurrent.ConcurrentHashMap;

public class TicketStatus {
    public int totalTicketsRemaining; // Number of tickets remaining in the pool
    public ConcurrentHashMap<String, Integer> vendorTicketsReleased; // Map to track tickets released by each vendor
    public ConcurrentHashMap<String, Integer> customerTicketsBought; // Map to track tickets bought by each customer
    public String message; // Additional message or status update

    // Constructor to initialize the TicketStatus object with necessary parameters
    public TicketStatus(int totalTicketsRemaining,
                        ConcurrentHashMap<String, Integer> vendorTicketsReleased,
                        ConcurrentHashMap<String, Integer> customerTicketsBought,
                        String message) {
        this.totalTicketsRemaining = totalTicketsRemaining;
        this.vendorTicketsReleased = vendorTicketsReleased;
        this.customerTicketsBought = customerTicketsBought;
        this.message = message;
    }
}