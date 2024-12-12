package com.example.ticketsystem.repository;

import com.example.ticketsystem.model.TicketTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketTransactionRepository extends MongoRepository<TicketTransaction, String> {
}
