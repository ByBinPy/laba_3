package org.example.exceptions;

public class InvalidBankIdException extends Exception
{
    public InvalidBankIdException(String message)
    {
        super(message);
    }
}
