package org.example.account;

import lombok.Getter;
import org.example.Transaction;

import java.util.List;

@Getter
public class CreditAccount extends Account
{
    private final int id;
    private double amount;
    private List<Transaction> transaction;

    public CreditAccount(int id, double amount, List<Transaction> transaction) {
        this.id = id;
        this.amount = amount;
        this.transaction = transaction;
    }

    @Override
    public double transaction(double diffAmount)
    {
        amount += diffAmount;
        return amount;
    }
}
