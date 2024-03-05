package org.example.declarations;

import lombok.Getter;
import lombok.Setter;
import org.example.exceptions.InvalidAmountException;
import org.example.exceptions.InvalidTransferAmountException;
import org.example.declarations.notifying.Subscriber;
import org.example.imp.records.Transaction;
import org.example.imp.services.Ticker;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Declaration API for bank accounts
 * and implementation general methods
 */
@Getter
public abstract class Account implements Subscriber
{
    private final double defaultAmount = 0;
    protected int id;
    protected int clientId;
    protected double amount = defaultAmount;
    @Setter
    double interest = defaultAmount;
    protected double hideDifferenceAmount = defaultAmount;
    protected String news = "";
    protected List<Transaction> transactionHistory = new ArrayList<>();
    public Account(int id, int clientId, double interest) {
        this.id = id;
        this.clientId = clientId;
        this.interest = interest;
    }
    public void increaseHideAmount() throws InvalidAmountException
    {
        if (amount < 0)
            throw new InvalidAmountException(String.format("Negative amount in account - > %d ", id));

        hideDifferenceAmount += amount*interest/365;
    }
    public double refill(double amount)
    {
        this.amount += amount;
        transactionHistory.add(new Transaction(transactionHistory.getLast().id(), Ticker.getTicker().getDay(), amount));
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
    public void approveHideAmount()
    {
        amount += hideDifferenceAmount;
    }
    public abstract double withdrawal(double amount) throws InvalidTransferAmountException;
    public abstract double transfer(double transferAmount) throws InvalidTransferAmountException;

}
