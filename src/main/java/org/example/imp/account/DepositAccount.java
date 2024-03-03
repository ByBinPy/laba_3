package org.example.imp.account;

import org.example.declarations.Account;
import org.example.exceptions.InvalidTransferAmountException;

public class DepositAccount extends Account
{
    public DepositAccount(int id,int clientId) {
        super(id,clientId);
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
    public double transfer(double transferAmount) throws InvalidTransferAmountException
    {
        if ((transferAmount + amount < 0 && amount >= 0) || (amount < 0 && transferAmount < 0))
        {
            throw new InvalidTransferAmountException("result amount less 0");
        }
        amount += transferAmount;
        return amount;
    }

    @Override
    public void update(String message) {
        news = message;
    }
}

