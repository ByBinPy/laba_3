package org.example.exceptions;

public class InvalidTransactionId extends Exception
{
    public InvalidTransactionId(String message)
    {
        super(message);
    }
}
