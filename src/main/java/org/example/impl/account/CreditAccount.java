package org.example.impl.account;

import org.example.declarations.Account;

public class CreditAccount extends Account
{
    public CreditAccount(int id,int clientId) {
        super(id,clientId);
    }

    @Override
    public double withdrawal(double amount)
    {
        this.amount -= amount;
        return this.amount;
    }
    @Override
    public double transfer(double transferAmount)
    {
        amount += transferAmount;
        return amount;
    }

    @Override
    public void update(String message) {
        news = message;
    }
}
