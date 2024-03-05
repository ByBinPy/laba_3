package org.example.exceptions;

/**
 * Exception when in bank`s operation was got wrong AccountID
 * could call in @see BankImp#transfer
 */
public class InvalidAccountIdException extends Exception
{
    public InvalidAccountIdException(String message)
    {
        super(message);
    }

}
