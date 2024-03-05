package org.example.imp.accounts;

import org.example.declarations.Account;

/**
 * Credit account
 * amount can be less 0
 * when happens recalculating account bank withdrawal commission
 */
public class CreditAccount extends Account
{
    public CreditAccount(int id,int clientId, double interest) {
        super(id,clientId,interest);
    }
    @Override
    public void increaseHideAmount()
    {
        if (amount < 0)
        {
            hideDifferenceAmount += amount*getInterest()/365;
        }
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
