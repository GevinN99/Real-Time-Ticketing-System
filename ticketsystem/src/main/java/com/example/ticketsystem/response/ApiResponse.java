package com.example.ticketsystem.response;

import com.example.ticketsystem.model.SystemConfiguration;

public class ApiResponse {
    private boolean success;
    private String message;
    private SystemConfiguration data;

    public ApiResponse(boolean success, String message, SystemConfiguration data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public String getMessage() {
        return message;
    }

    public SystemConfiguration getData() {
        return data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(SystemConfiguration data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }
}
