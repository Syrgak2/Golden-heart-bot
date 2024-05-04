package com.example.golden.heart.bot.exception;

public class UnknownUpdateException extends RuntimeException {
    public UnknownUpdateException() {
    }

    public UnknownUpdateException(String message) {
        super(message);
    }
}
