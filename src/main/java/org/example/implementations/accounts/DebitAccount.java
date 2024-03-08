package org.example.implementations.accounts;

import org.example.declarations.Account;
import org.example.exceptions.InvalidTransferAmountException;
import org.example.implementations.records.BaseTransaction;
import org.example.implementations.services.Ticker;

/**
 * Debit account
 * amount cannot be less 0
 * when happens recalculating account bank refill interest on balance
 */
public class DebitAccount extends Account {

    public DebitAccount(int id,int clientId, double interest) {
        super(id, clientId, interest);
    }

    @Override
    public double withdrawal(double amount) throws InvalidTransferAmountException
    {

        if (this.amount - amount < 0)
            throw new InvalidTransferAmountException("try refill amount great then currency amount");
        this.amount -= amount;

        transactionHistory.add(transactionHistory.isEmpty() ?
                new BaseTransaction(1, Ticker.getTicker().getDay(), -amount) :
                new BaseTransaction(transactionHistory.getLast().id+1, Ticker.getTicker().getDay(), -amount));

        return this.amount;
    }

    @Override
    public void update(String message) {
        news = message;
    }
}
