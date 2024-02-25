package org.example.exceptions;

/**
 * Exception class that can be called in @see CentralBank#registration
 */
public class InvalidDataForRegistrationBankException extends Exception
{
    public InvalidDataForRegistrationBankException(String message)
    {
        super(message);
    }
}
