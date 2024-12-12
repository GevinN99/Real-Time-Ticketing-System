package com.example.ticketsystem.component;

import com.example.ticketsystem.dto.TicketStatus;
import com.example.ticketsystem.websocket.TicketStatusHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TicketPool {
    private final ConcurrentLinkedQueue<String> tickets = new ConcurrentLinkedQueue<>(); // Queue to hold tickets
    private final TicketStatusHandler ticketStatusHandler; // Handler to send ticket status updates
    private int initalTotalTickets; // Initial total number of tickets
    private int totalTicketsRemaining; // Total tickets remaining in the pool
    private int totalTicketsPurchased; // Total tickets purchased so far
    private final int maxTicketCapacity; // Maximum capacity of the ticket pool
    private final ReentrantLock lock = new ReentrantLock(); // Lock for thread safety
    private final Condition notFull = lock.newCondition(); // Condition to wait when the pool is full
    private final Condition notEmpty = lock.newCondition(); // Condition to wait when the pool is empty
    final ConcurrentHashMap<String, Integer> vendorTicketsReleased = new ConcurrentHashMap<>(); // Map to track tickets released by vendors
    final ConcurrentHashMap<String, Integer> customerTicketsBought = new ConcurrentHashMap<>(); // Map to track tickets bought by customers
    private final ObjectMapper objectMapper = new ObjectMapper(); // ObjectMapper for JSON conversion
    private int count = 1; // Counter for status updates

    // Constructor to initialize the TicketPool with necessary parameters
    public TicketPool(int maxTicketCapacity, int totalTickets, TicketStatusHandler ticketStatusHandler) {
        this.maxTicketCapacity = maxTicketCapacity;
        this.totalTicketsRemaining = totalTickets;
        this.initalTotalTickets = totalTickets;
        this.ticketStatusHandler = ticketStatusHandler;
    }

    // Method to add tickets to the pool
    public int addTicket(int count) {
        lock.lock();
        try {
            int addedCount = 0;
            for (int i = 0; i < count; i++) {
                if (tickets.size() < maxTicketCapacity && totalTicketsRemaining > 0) {
                    tickets.add("Ticket-" + (totalTicketsRemaining--));
                    notEmpty.signalAll(); // Signal that tickets are available
                    addedCount++;
                } else {
                    while (tickets.size() >= maxTicketCapacity) {
                        notFull.await(); // Wait for space to become available
                    }
                }
            }
            notEmpty.signalAll(); // Signal that tickets are available
            return addedCount;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return 0;
        } finally {
            lock.unlock();
        }
    }

    // Method to remove a ticket from the pool
    public String removeTicket() {
        lock.lock();
        try {
            while (tickets.isEmpty() && totalTicketsRemaining > 0) {
                notEmpty.await(); // Wait for tickets to be added
            }

            // If tickets are still empty, return null
            if (tickets.isEmpty() && totalTicketsRemaining == 0) {
                return null;
            }

            String ticket = tickets.poll();
            totalTicketsPurchased++;
            notFull.signalAll(); // Signal that space is available
            return ticket;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Handle interruption
            return null;
        } finally {
            lock.unlock();
        }
    }

    // Method to clear all tickets from the pool
    public void clearTickets() {
        System.out.println("Clearing all tickets and resetting...");
        tickets.clear();
    }

    // Method to get the total tickets remaining
    public int getTotalTicketsRemaining() {
        lock.lock();
        try {
            return totalTicketsRemaining;
        } finally {
            lock.unlock();
        }
    }

    // Method to get the total tickets purchased
    public int getTotalTicketsPurchased() {
        lock.lock();
        try {
            return totalTicketsPurchased;
        } finally {
            lock.unlock();
        }
    }

    // Method to send a ticket status update
    public void sendTicketStatusUpdate(String additionalMessage) {
        lock.lock();
        try {
            TicketStatus status = new TicketStatus(
                    initalTotalTickets - getTotalTicketsPurchased(),
                    vendorTicketsReleased,
                    customerTicketsBought,
                    additionalMessage
            );
            String jsonStatus = objectMapper.writeValueAsString(status);
            System.out.println(++count + jsonStatus);
            ticketStatusHandler.sendTicketStatusUpdate(jsonStatus);
        } catch (Exception e) {
            System.err.println("Error sending ticket status update: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }
}