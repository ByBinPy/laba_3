package org.example.exceptions;

/**
 * Exception when at registration was got wrong data
 * could call in @see CentralBank#registration
 */
public class InvalidDataForRegistrationBankException extends Exception {
    public InvalidDataForRegistrationBankException(String message) {
        super(message);
    }
}
