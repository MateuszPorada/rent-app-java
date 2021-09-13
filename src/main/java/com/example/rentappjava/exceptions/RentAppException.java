package com.example.rentappjava.exceptions;

public class RentAppException extends RuntimeException {
    public RentAppException(String message, Exception exception) {
        super(message, exception);
    }

    public RentAppException(String message) {
        super(message);
    }
}
