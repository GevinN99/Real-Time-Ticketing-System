package com.example.ticketsystem.service;

import com.example.ticketsystem.model.TicketTransaction;
import com.example.ticketsystem.repository.TicketTransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TicketTransactionService {

    private final TicketTransactionRepository ticketTransactionRepository; // Repository to manage ticket transactions

    // Constructor to initialize the TicketTransactionService with the TicketTransactionRepository
    public TicketTransactionService(TicketTransactionRepository ticketTransactionRepository) {
        this.ticketTransactionRepository = ticketTransactionRepository;
    }

    // Method to log a ticket transaction
    public void logTransaction(String transactionType, String userName, int ticketCount) {
        TicketTransaction transaction = new TicketTransaction(); // Create a new TicketTransaction object
        transaction.setTransactionType(transactionType); // Set the type of transaction (e.g., "purchase" or "release")
        transaction.setUserName(userName); // Set the name of the user involved in the transaction
        transaction.setTicketCount(ticketCount); // Set the number of tickets involved in the transaction
        transaction.setTimestamp(LocalDateTime.now()); // Set the timestamp of the transaction

        ticketTransactionRepository.save(transaction); // Save the transaction to the repository
    }
}