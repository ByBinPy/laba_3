package org.example.declarations;

import lombok.Getter;
import lombok.Setter;
import org.example.exceptions.InvalidRefillAmountException;
import org.example.exceptions.InvalidTransactionIdException;
import org.example.exceptions.InvalidTransferAmountException;
import org.example.declarations.notifying.Subscriber;
import org.example.implementations.records.BaseTransaction;
import org.example.implementations.records.Transfer;
import org.example.implementations.services.Ticker;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Declaration API for bank accounts
 * and implementation general methods
 */
@Getter
public abstract class Account implements Subscriber {
    private final double defaultAmount = 0;
    protected int id;
    protected int clientId;
    protected double amount = defaultAmount;
    @Setter
    double interest;
    protected double hideDifferenceAmount = defaultAmount;
    protected String news = "";
    protected List<Transaction> transactionHistory = new ArrayList<>();

    public Account(int id, int clientId, double interest) {
        this.id = id;
        this.clientId = clientId;
        this.interest = interest;
    }

    public void increaseHideAmount() {
        hideDifferenceAmount += amount * interest / 365;
    }

    public double refill(double amount) throws InvalidRefillAmountException {
        if (amount < 0)
            throw new InvalidRefillAmountException(String.format("Negative amount in refill operation in account %d", id));

        this.amount += amount;
        transactionHistory.add(transactionHistory.isEmpty() ?
                new BaseTransaction(1, Ticker.getTicker().getDay(), amount) :
                new BaseTransaction(transactionHistory.getLast().id + 1, Ticker.getTicker().getDay(), amount));

        return this.amount;
    }

    public void transfer(int externalBank, int externalAccount, double transferAmount) throws InvalidTransferAmountException {
        // int id, int day, double differenceAmount, int externalId, int externalAccountId, int externalBankId
        if (amount + transferAmount < 0)
            throw new InvalidTransferAmountException("Result amount would be less zero");

        amount += transferAmount;
        transactionHistory.add(transactionHistory.isEmpty() ?
                new Transfer(1, Ticker.getTicker().getDay(), transferAmount, externalBank, externalAccount) :
                new Transfer(transactionHistory.getLast().id + 1, Ticker.getTicker().getDay(), transferAmount, externalBank, externalAccount));
    }

    public boolean baseCancellation(int transactionId) {
        Optional<Transaction> transaction = transactionHistory.stream().filter(t -> t.id == transactionId).findFirst();
        if (transaction.isEmpty())
            return false;

        amount += transaction.get().differenceAmount;
        transactionHistory = transactionHistory.stream().filter(t -> t.id != transactionId).collect(Collectors.toList());

        return true;
    }

    public void transferCancellation(int externalBankId, int externalAccountId, double transferAmount) throws InvalidTransactionIdException {
        Optional<Transaction> transfer = transactionHistory.stream()
                .filter(transaction -> transaction instanceof Transfer
                        && ((Transfer) transaction).externalAccountId == externalAccountId
                        && ((Transfer) transaction).externalBankId == externalBankId
                        && transaction.differenceAmount == transferAmount).findFirst();

        if (transfer.isEmpty())

            throw new InvalidTransactionIdException(String.format("Unknown transaction id in %d account", externalAccountId));

        amount -= transfer.get().differenceAmount;
        transactionHistory.remove(transfer.get());
    }

    public Transfer transferCancellation(int transactionId) throws InvalidTransactionIdException {
        Optional<Transaction> transfer = transactionHistory
                .stream()
                .filter(transaction -> transaction instanceof Transfer
                        && transaction.id == transactionId)
                .findFirst();

        if (transfer.isEmpty())
            throw new InvalidTransactionIdException(String.format("Unknown transaction id in %d account", transactionId));

        amount -= transfer.get().differenceAmount;

        transactionHistory.stream()
                .filter(transaction -> transaction instanceof Transfer
                        && transaction.id == transactionId)
                .findFirst()
                .ifPresent(
                        transaction ->
                                transactionHistory.remove(transaction));

        return (Transfer) transfer.get();
    }

    public void approveHideAmount() {
        amount += hideDifferenceAmount;
        hideDifferenceAmount = 0;
    }

    public abstract double withdrawal(double amount) throws InvalidTransferAmountException;

}
