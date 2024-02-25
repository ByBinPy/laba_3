package org.example.exceptions;
/**
 * Exception class that can be called in @see CentralBank#transfer
 */

public class InvalidTransferAmountException extends Exception
{
    public InvalidTransferAmountException(String message)
    {
        super(message);
    }
}
