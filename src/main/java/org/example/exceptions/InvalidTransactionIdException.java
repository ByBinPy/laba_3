package org.example.exceptions;

/**
 * Exception when in bank`s cancellation was got wrong TransactionID
 */
public class InvalidTransactionIdException extends Exception
{
    public InvalidTransactionIdException(String message)
    {
        super(message);
    }
}
