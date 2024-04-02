package org.example.exceptions;

/**
 * Exception when amount less than 0
 * could call in @see BankImp#dayRecalculate @see CentralBank#notifyBanksAboutNewDay and @see Account#increaseHideAmount
 */
public class InvalidAmountException extends Exception {
    public InvalidAmountException(String message) {
        super(message);
    }
}
