package org.example.exceptions;

/**
 * Exception when in bank`s operation was refill amount less 0
 */
public class InvalidRefillAmountException extends Exception
{
    public InvalidRefillAmountException(String message)
    {
        super(message);
    }
}
