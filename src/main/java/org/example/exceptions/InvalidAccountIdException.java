package org.example.exceptions;

public class InvalidAccountIdException extends Exception
{
    public InvalidAccountIdException(String message)
    {
        super(message);
    }

}
