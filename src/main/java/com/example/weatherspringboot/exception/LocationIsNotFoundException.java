package com.example.weatherspringboot.exception;

public class LocationIsNotFoundException extends RuntimeException {
    public LocationIsNotFoundException(String message) {
        super(message);
    }
}
