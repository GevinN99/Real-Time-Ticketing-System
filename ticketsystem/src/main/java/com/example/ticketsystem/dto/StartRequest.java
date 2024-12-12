package com.example.ticketsystem.dto;
import java.util.List;

public class StartRequest {
    private List<String> vendors;
    private List<String> customers;

    // Getters and Setters
    public List<String> getVendors() {
        return vendors;
    }

    public void setVendors(List<String> vendors) {
        this.vendors = vendors;
    }

    public List<String> getCustomers() {
        return customers;
    }

    public void setCustomers(List<String> customers) {
        this.customers = customers;
    }
}

