package org.example.exceptions;

public class InvalidClientIdException extends Exception
{
    public InvalidClientIdException(String message)
    {
        super(message);
    }
}
