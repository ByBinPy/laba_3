package org.example.account;

import lombok.Getter;
import org.example.clients.Transaction;
import org.example.exceptions.InvalidTransferAmountException;

import java.util.List;

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
}
