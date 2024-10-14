package org.example.exception;

public class NotFoundByOrdinalException extends RuntimeException {
    public NotFoundByOrdinalException(String message) {
        super(message);
    }
}