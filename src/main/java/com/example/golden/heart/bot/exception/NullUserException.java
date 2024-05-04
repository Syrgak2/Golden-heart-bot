package com.example.golden.heart.bot.exception;

public class NullUserException extends RuntimeException {
    public NullUserException() {
    }

    public NullUserException(String message) {
        super(message);
    }
}
