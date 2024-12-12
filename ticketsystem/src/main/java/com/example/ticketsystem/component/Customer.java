package com.example.ticketsystem.component;

import com.example.ticketsystem.service.ConfigurationService;
import com.example.ticketsystem.service.TicketTransactionService;

public class Customer implements Runnable {
    private final TicketPool ticketPool; // Pool of tickets available for purchase
    private final int customerRetrievalRate; // Rate at which the customer attempts to retrieve tickets
    private final ConfigurationService configurationService; // Service to manage configuration settings
    private TicketTransactionService ticketTransactionService; // Service to log ticket transactions

    // Constructor to initialize the Customer object with necessary services and parameters
    public Customer(TicketPool ticketPool, int customerRetrievalRate, ConfigurationService configurationService, TicketTransactionService ticketTransactionService) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.configurationService = configurationService;
        this.ticketTransactionService = ticketTransactionService;
    }

    @Override
    public void run() {
        try {
            // Loop to keep purchasing tickets while the system is running and the thread is not interrupted
            while (configurationService.isRunning() && !Thread.currentThread().isInterrupted()) {
                int ticketsToPurchase = customerRetrievalRate; // Number of tickets to attempt to purchase
                int ticketsPurchased = 0; // Counter for successfully purchased tickets

                // Loop to attempt purchasing the specified number of tickets
                for (int i = 0; i < ticketsToPurchase; i++) {
                    String ticket = ticketPool.removeTicket(); // Remove a ticket from the pool

                    if (ticket != null) {
                        // Update the count of tickets bought by this customer
                        ticketPool.customerTicketsBought.merge(Thread.currentThread().getName(), 1, Integer::sum);
                        // Send a status update about the ticket purchase
                        ticketPool.sendTicketStatusUpdate("Customer " + Thread.currentThread().getName() + " purchased " + ticket);
                        ticketsPurchased++;
                    } else {
                        // If no tickets are available, stop the system
                        System.out.println("All tickets have been purchased. Stopping the system...");
                        configurationService.stop();
                        break;
                    }
                }

                // If tickets were purchased, log the transaction
                if (ticketsPurchased > 0) {
                    ticketTransactionService.logTransaction("purchase", Thread.currentThread().getName(), ticketsPurchased);
                    System.out.println("Customer " + Thread.currentThread().getName() + " purchased " + ticketsPurchased + " tickets.");
                }

                // Sleep to control the purchase rate (simulating customer retrieval rate)
                Thread.sleep(1000 / customerRetrievalRate); // Adjust sleep to fit the retrieval rate
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Properly handle thread interruption
        }
    }
}