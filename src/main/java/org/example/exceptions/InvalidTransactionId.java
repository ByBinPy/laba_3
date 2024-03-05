package org.example.exceptions;

/**
 * Exception when in bank`s cancellation was got wrong TransactionID
 */
public class InvalidTransactionId extends Exception
{
    public InvalidTransactionId(String message)
    {
        super(message);
    }
}
