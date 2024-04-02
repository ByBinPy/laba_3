package org.example.implementations.accounts;

import org.example.declarations.Account;
import org.example.implementations.records.BaseTransaction;
import org.example.implementations.services.Ticker;

/**
 * Credit account
 * amount can be less 0
 * when happens recalculating account bank withdrawal commission
 */
public class CreditAccount extends Account {
    public CreditAccount(int id, int clientId, double interest) {
        super(id, clientId, interest);
    }

    @Override
    public void increaseHideAmount() {
        if (amount < 0) {
            hideDifferenceAmount += amount * getInterest() / 365;
        }
    }

    @Override
    public double withdrawal(double amount) {
        this.amount -= amount;
        transactionHistory.add(transactionHistory.isEmpty() ?
                new BaseTransaction(1, Ticker.getTicker().getDay(), -amount) :
                new BaseTransaction(transactionHistory.getLast().id + 1, Ticker.getTicker().getDay(), -amount));

        return this.amount;
    }

    @Override
    public void update(String message) {
        news = message;
    }
}
