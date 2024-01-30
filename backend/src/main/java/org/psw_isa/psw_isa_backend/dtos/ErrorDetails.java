package org.psw_isa.psw_isa_backend.dtos;

public class ErrorDetails {
    private String message;
    private String details;

    public ErrorDetails() {
        super();
    }

    public ErrorDetails(String message, String details) {
        super();
        this.message = message;
        this.details = details;
    }

    public String getMessage() {
        return this.message;
    }

    public String getDetails() {
        return this.details;
    }
    
}
