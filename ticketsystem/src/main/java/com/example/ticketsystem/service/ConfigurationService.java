package com.example.ticketsystem.service;

import com.example.ticketsystem.component.Customer;
import com.example.ticketsystem.component.TicketPool;
import com.example.ticketsystem.component.Vendor;
import com.example.ticketsystem.model.SystemConfiguration;
import com.example.ticketsystem.repository.ConfigurationRepository;
import com.example.ticketsystem.response.ApiResponse;
import com.example.ticketsystem.dto.StartRequest;
import com.example.ticketsystem.websocket.TicketStatusHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConfigurationService {
    private final TicketTransactionService ticketTransactionService; // Service to log ticket transactions
    private SystemConfiguration configuration; // Current system configuration
    private final ConfigurationRepository configurationRepository; // Repository to manage configurations
    private TicketPool ticketPool; // Pool of tickets to be managed
    private final TicketStatusHandler ticketStatusHandler; // Handler to send ticket status updates
    private final List<Thread> threads = new ArrayList<>(); // List to store running threads
    private boolean isRunning = false; // Flag to indicate if the system is running

    // Constructor to initialize the ConfigurationService with necessary services and repository
    public ConfigurationService(ConfigurationRepository configurationRepository,
                                TicketStatusHandler ticketStatusHandler,
                                TicketTransactionService ticketTransactionService) {
        this.configurationRepository = configurationRepository;
        this.ticketStatusHandler = ticketStatusHandler;
        this.ticketTransactionService = ticketTransactionService;
    }

    // Method to create a new system configuration
    public ApiResponse createConfiguration(SystemConfiguration newConfig) {
        if (isRunning) {
            stop(); // Stop the system if it is currently running
        }

        String validationError = validateConfiguration(newConfig); // Validate the new configuration
        if (validationError != null) {
            return new ApiResponse(false, validationError); // Return error response if validation fails
        }

        configurationRepository.deleteAll(); // Delete all existing configurations
        configurationRepository.save(newConfig); // Save the new configuration
        return new ApiResponse(true, "Configuration created successfully.", newConfig); // Return success response
    }

    // Method to update the existing system configuration
    public ApiResponse updateConfiguration(SystemConfiguration newConfig) {
        if (isRunning) {
            stop(); // Stop the system if it is currently running
        }

        String validationError = validateConfiguration(newConfig); // Validate the new configuration
        if (validationError != null) {
            return new ApiResponse(false, validationError); // Return error response if validation fails
        }

        configuration = configurationRepository.findAll().getFirst(); // Retrieve the current configuration

        // Update the configuration with new values
        configuration.setTotalTickets(newConfig.getTotalTickets());
        configuration.setTicketReleaseRate(newConfig.getTicketReleaseRate());
        configuration.setCustomerRetrievalRate(newConfig.getCustomerRetrievalRate());
        configuration.setMaxTicketCapacity(newConfig.getMaxTicketCapacity());

        configurationRepository.save(configuration); // Save the updated configuration
        return new ApiResponse(true, "Configuration updated successfully.", configuration); // Return success response
    }

    // Method to get the current system configuration
    public ApiResponse getConfiguration() {
        configuration = configurationRepository.findAll().stream().findFirst().orElse(null); // Retrieve the current configuration
        if (configuration == null) {
            System.out.println("no config found");
            return new ApiResponse(false, "No configuration found."); // Return error response if no configuration is found
        }
        return new ApiResponse(true, "Configuration retrieved successfully.", configuration); // Return success response
    }

    // Method to start the system with the provided start request
    public ApiResponse start(StartRequest startRequest) {
        if (!isRunning) {
            configuration = configurationRepository.findAll().getFirst(); // Retrieve the current configuration
            System.out.println("System Started");
            System.out.println(configuration.toString());

            ticketPool = new TicketPool(configuration.getMaxTicketCapacity(), configuration.getTotalTickets(), ticketStatusHandler); // Initialize the ticket pool

            // Create lists to store threads
            List<Thread> vendorThreads = new ArrayList<>();
            List<Thread> customerThreads = new ArrayList<>();

            // Dynamically create vendor threads
            for (String vendorName : startRequest.getVendors()) {
                Vendor vendor = new Vendor(ticketPool, configuration.getTicketReleaseRate(), this, ticketTransactionService);
                Thread vendorThread = new Thread(vendor, vendorName);
                System.out.println("Vendor thread created: " + vendorThread.getName());
                vendorThreads.add(vendorThread);
            }

            // Dynamically create customer threads
            for (String customerName : startRequest.getCustomers()) {
                Customer customer = new Customer(ticketPool, configuration.getCustomerRetrievalRate(), this, ticketTransactionService);
                Thread customerThread = new Thread(customer, customerName);
                System.out.println("Customer thread created: " + customerThread.getName());
                customerThreads.add(customerThread);
            }

            // Add all threads (vendors and customers) to the main threads list
            threads.addAll(vendorThreads);
            threads.addAll(customerThreads);

            isRunning = true; // Set the system running flag to true

            for (Thread thread : threads) {
                thread.start(); // Start all threads
            }
            return new ApiResponse(true, "System started successfully."); // Return success response
        } else {
            return new ApiResponse(false, "System is already running."); // Return error response if the system is already running
        }
    }

    // Method to stop the system
    public ApiResponse stop() {
        if (isRunning) {
            isRunning = false; // Set the system running flag to false

            for (Thread thread : threads) {
                thread.interrupt(); // Interrupt all running threads
            }

            threads.clear(); // Clear the threads list
            System.out.println("\nSystem stopped");
            return new ApiResponse(true, "System stopped successfully."); // Return success response
        } else {
            return new ApiResponse(false, "System is not yet started."); // Return error response if the system is not running
        }
    }

    // Method to reset the system
    public ApiResponse reset() {
        if (isRunning) {
            stop(); // Stop the system if it is currently running
            if (ticketPool != null) {
                ticketPool.clearTickets(); // Clear all tickets from the pool
            }

            ticketPool = new TicketPool(configuration.getMaxTicketCapacity(), configuration.getTotalTickets(), ticketStatusHandler); // Reinitialize the ticket pool
            return new ApiResponse(true, "System reset successfully."); // Return success response
        } else {
            return new ApiResponse(false, "System is not yet started."); // Return error response if the system is not running
        }
    }

    // Method to check if the system is running
    public boolean isRunning() {
        return isRunning;
    }

    // Method to validate the system configuration
    private String validateConfiguration(SystemConfiguration config) {
        if (config.getTotalTickets() <= 0) {
            return "Total tickets must be greater than 0.";
        }
        if (config.getTicketReleaseRate() <= 0) {
            return "Ticket release rate must be greater than 0.";
        }
        if (config.getCustomerRetrievalRate() <= 0) {
            return "Customer retrieval rate must be greater than 0.";
        }
        if (config.getMaxTicketCapacity() <= 0) {
            return "Max ticket capacity must be greater than 0.";
        }
        if (config.getMaxTicketCapacity() > config.getTotalTickets()) {
            return "Max ticket capacity cannot exceed total tickets.";
        }
        return null; // Return null if no validation errors
    }
}