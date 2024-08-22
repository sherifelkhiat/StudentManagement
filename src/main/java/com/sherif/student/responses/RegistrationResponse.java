package com.sherif.student.responses;

import lombok.Builder;

@Builder
public class RegistrationResponse {
    private String status;
    private String message;

    // Getters and setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

