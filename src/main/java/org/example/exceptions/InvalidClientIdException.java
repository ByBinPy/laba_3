package org.example.exceptions;

/**
 * Exception when in bank`s operation was got wrong ClientID
 */
public class InvalidClientIdException extends Exception {
    public InvalidClientIdException(String message) {
        super(message);
    }
}
