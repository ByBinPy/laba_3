package org.example.account;

import lombok.Getter;
import org.example.Transaction;
import org.example.exceptions.InvalidTransferAmountException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Account
{
    private final double defaultAmount = 0;
    int id;
    int clientId;
    double amount;

    List<Transaction> transactionHistory;
    public Account(int id, int clientId) {
        this.id = id;
        this.clientId = clientId;
        amount = defaultAmount;
        transactionHistory = new ArrayList<>();
    }
    public double refill(double amount)
    {
        this.amount += amount;
        transactionHistory.add(new Transaction(LocalTime.now(), amount));
        return this.amount;
    }
    public abstract double withdrawal(double amount) throws InvalidTransferAmountException;
    public abstract double transfer(double transferAmount) throws InvalidTransferAmountException;

}
