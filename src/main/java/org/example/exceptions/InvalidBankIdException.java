package org.example.exceptions;

/**
 * Exception when in bank`s operation was got wrong BankID
 */
public class InvalidBankIdException extends Exception {
    public InvalidBankIdException(String message) {
        super(message);
    }
}
