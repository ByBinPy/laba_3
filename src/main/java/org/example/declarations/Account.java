package org.example.declarations;

import lombok.Getter;
import org.example.impl.clients.Transaction;
import org.example.exceptions.InvalidTransferAmountException;
import org.example.declarations.notifying.Subscriber;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public abstract class Account implements Subscriber
{
    private final double defaultAmount = 0;
    protected int id;
    protected int clientId;
    protected double amount;

    protected String news = "";

    protected List<Transaction> transactionHistory;
    public Account(int id, int clientId) {
        this.id = id;
        this.clientId = clientId;
        amount = defaultAmount;
        transactionHistory = new ArrayList<>();
    }
    public double refill(double amount)
    {
        this.amount += amount;
        transactionHistory.add(new Transaction(transactionHistory.getLast().id(), LocalTime.now(), amount));
        return this.amount;
    }
    public boolean cancellation(int transactionId)
    {
        Optional<Transaction>transaction =  transactionHistory.stream().filter(t -> t.id() == transactionId).findFirst();
        if (transaction.isEmpty())
            return false;
        amount += transaction.get().differenceAmount();
        transactionHistory = transactionHistory.stream().filter(t -> t.id() != transactionId).collect(Collectors.toList());
        return true;
    }
    public abstract double withdrawal(double amount) throws InvalidTransferAmountException;
    public abstract double transfer(double transferAmount) throws InvalidTransferAmountException;

}
