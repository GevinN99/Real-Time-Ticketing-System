package com.example.ticketsystem.controller;

import com.example.ticketsystem.model.SystemConfiguration;
import com.example.ticketsystem.response.ApiResponse;
import com.example.ticketsystem.dto.StartRequest;
import com.example.ticketsystem.service.ConfigurationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/config")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ConfigurationController {
    private final ConfigurationService configurationService; // Service to manage system configuration

    // Constructor to initialize the ConfigurationController with the ConfigurationService
    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    // Endpoint to get the current system configuration
    @GetMapping
    public ResponseEntity<ApiResponse> getConfiguration() {
        ApiResponse response = configurationService.getConfiguration();
        if (response.isSuccess()) {
            return ResponseEntity.ok(response); // Return 200 OK if successful
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Return 400 Bad Request if failed
        }
    }

    // Endpoint to create a new system configuration
    @PostMapping
    public ResponseEntity<ApiResponse> createConfiguration(@RequestBody SystemConfiguration config) {
        ApiResponse response = configurationService.createConfiguration(config);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response); // Return 200 OK if successful
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Return 400 Bad Request if failed
        }
    }

    // Endpoint to update the existing system configuration
    @PutMapping
    public ResponseEntity<ApiResponse> setConfiguration(@RequestBody SystemConfiguration config) {
        ApiResponse response = configurationService.updateConfiguration(config);
        return ResponseEntity.ok(response); // Always return 200 OK
    }

    // Endpoint to start the system with the provided start request
    @PostMapping("/start")
    public ResponseEntity<ApiResponse> startSystem(@RequestBody StartRequest startrequest) {
        ApiResponse response = configurationService.start(startrequest);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response); // Return 200 OK if successful
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Return 400 Bad Request if failed
        }
    }

    // Endpoint to stop the system
    @PostMapping("/stop")
    public ResponseEntity<ApiResponse> stopSystem() {
        ApiResponse response = configurationService.stop();
        if (response.isSuccess()) {
            return ResponseEntity.ok(response); // Return 200 OK if successful
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Return 400 Bad Request if failed
        }
    }

    // Endpoint to reset the system
    @PostMapping("/reset")
    public ResponseEntity<ApiResponse> resetSystem() {
        ApiResponse response = configurationService.reset();
        if (response.isSuccess()) {
            return ResponseEntity.ok(response); // Return 200 OK if successful
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Return 400 Bad Request if failed
        }
    }

    // Endpoint to get the current status of the system (running or not)
    @GetMapping("/status")
    public ResponseEntity<Boolean> getSystemStatus() {
        boolean isRunning = configurationService.isRunning();
        return ResponseEntity.ok(isRunning); // Return 200 OK with the status
    }
}