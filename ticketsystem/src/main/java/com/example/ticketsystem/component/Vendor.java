package com.example.ticketsystem.component;

import com.example.ticketsystem.service.ConfigurationService;
import com.example.ticketsystem.service.TicketTransactionService;

public class Vendor implements Runnable {
    private final TicketPool ticketPool; // Pool of tickets to be managed
    private final int ticketReleaseRate; // Rate at which tickets are released into the pool
    private final ConfigurationService configurationService; // Service to manage configuration settings
    private TicketTransactionService ticketTransactionService; // Service to log ticket transactions

    // Constructor to initialize the Vendor object with necessary services and parameters
    public Vendor(TicketPool ticketPool, int ticketReleaseRate, ConfigurationService configurationService, TicketTransactionService ticketTransactionService) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.configurationService = configurationService;
        this.ticketTransactionService = ticketTransactionService;
    }

    @Override
    public void run() {
        try {
            // Loop to keep releasing tickets while the system is running and the thread is not interrupted
            while (configurationService.isRunning() && !Thread.currentThread().isInterrupted()) {
                if (ticketPool.getTotalTicketsRemaining() > 0) {
                    int addedTickets = ticketPool.addTicket(ticketReleaseRate); // Add tickets to the pool
                    if (addedTickets > 0) {
                        // Update the count of tickets released by this vendor
                        ticketPool.vendorTicketsReleased.merge(Thread.currentThread().getName(), addedTickets, Integer::sum);
                        // Send a status update about the ticket release
                        ticketPool.sendTicketStatusUpdate("Vendor " + Thread.currentThread().getName() + " released " + addedTickets + " tickets");

                        // Log the ticket release transaction
                        ticketTransactionService.logTransaction("release", Thread.currentThread().getName(), addedTickets);

                        // Sleep to control the release rate (simulating vendor release rate)
                        Thread.sleep(1000 / ticketReleaseRate);
                    }
                } else {
                    break; // Stop if no tickets are remaining
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Properly handle thread interruption
        }
    }
}