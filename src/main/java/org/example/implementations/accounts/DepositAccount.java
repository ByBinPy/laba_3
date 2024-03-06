package org.example.implementations.accounts;

import org.example.declarations.Account;
import org.example.exceptions.InvalidTransferAmountException;
/**
 * Deposit account
 * amount cannot be less 0
 * when happens recalculating account bank refill interest on balance
 */
public class DepositAccount extends Account
{
    public DepositAccount(int id,int clientId, double interest) {
        super(id,clientId,interest);
    }

    @Override
    public void approveHideAmount() {
        amount += hideDifferenceAmount;
        hideDifferenceAmount = 0;
    }

    @Override
    public double withdrawal(double amount) throws InvalidTransferAmountException
    {

        if (this.amount - amount < 0)
            throw new InvalidTransferAmountException("try refill amount great then currency amount");
        this.amount -= amount;
        return this.amount;
    }

    @Override
    public void update(String message) {
        news = message;
    }
}

