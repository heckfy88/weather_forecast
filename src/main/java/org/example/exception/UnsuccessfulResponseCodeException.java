package org.example.exception;

public class UnsuccessfulResponseCodeException extends RuntimeException {
    public UnsuccessfulResponseCodeException(String message) {
        super(message);
    }
}
