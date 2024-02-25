package org.example.exceptions;

public class InvalidRefillAmountException extends Exception
{
    public InvalidRefillAmountException(String message)
    {
        super(message);
    }
}
