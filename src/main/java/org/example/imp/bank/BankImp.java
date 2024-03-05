package org.example.imp.bank;

import jdk.jshell.spi.ExecutionControl;
import lombok.Getter;
import lombok.NonNull;
import org.example.declarations.Account;
import org.example.declarations.Bank;
import org.example.declarations.Client;
import org.example.declarations.notifying.Publisher;
import org.example.declarations.notifying.Subscriber;
import org.example.exceptions.*;
import org.example.imp.clients.ClientImp;
import org.example.imp.account.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class where users and accounts are stored
 * and which provides API for bank`s operations
 */
@Getter
public class BankImp implements Bank, Publisher {
    private final int bankId;
    private double debitInterest;
    private double depositInterest;
    private double creditCommission;
    private final List<Client> clients;
    private final List<Subscriber> subscribers;
    private final List<Account> accounts;

    public BankImp(int id, double debit, double deposit, double credit) {
        bankId = id;
        debitInterest = debit;
        depositInterest = deposit;
        creditCommission = credit;
        clients = new ArrayList<>();
        accounts = new ArrayList<>();
        subscribers = new ArrayList<>();
    }

    public void registrationClient(@NonNull String name, @NonNull String surname, String address, int passportNumber) {
        if (clients.isEmpty())
            clients.add(
                    ClientImp.builder()
                            .id(1)
                            .name(name)
                            .surname(surname)
                            .address(address)
                            .passportNumber(passportNumber)
                            .build());
        else
            clients.add(
                    ClientImp.builder()
                            .id(clients.getLast().getId() + 1)
                            .name(name)
                            .surname(surname)
                            .address(address)
                            .passportNumber(passportNumber)
                            .build());

    }
    public void registrationAccount(int clientId, AccountType type) throws ExecutionControl.NotImplementedException
    {
        Account addedAccount;
        int id = accounts.isEmpty() ? 1 : accounts.getLast().getId();
        switch (type)
        {
            case Debit -> addedAccount = new DebitAccount(id, clientId, debitInterest);
            case Credit -> addedAccount = new CreditAccount(id, clientId, creditCommission);
            case Deposit -> addedAccount = new DepositAccount(id, clientId, depositInterest);
            default -> throw new ExecutionControl.NotImplementedException(" ");
        }
        accounts.add(addedAccount);
    }
    public void transfer(int toBankId,int toAccountId, double transferAmount)
            throws InvalidBankIdException,
            InvalidAccountIdException,
            InvalidTransferAmountException {

        Optional<Bank>toBank = CentralBank.getInstance().getBankByID(toBankId);
        if (toBank.isEmpty())
            throw new InvalidBankIdException(String.format("Non-exist bank %d in transfer happened in %d",toBankId, bankId));

        Optional<Account> toAccount = toBank.get().getAccountById(toAccountId);
        if (toAccount.isEmpty())
            throw new InvalidAccountIdException(String.format("Non-exist account %d in transfer in bank %d",toAccountId, toBankId));

        toAccount.get().withdrawal(transferAmount);
    }
    public void setDebitInterest(double interest)
    {
        debitInterest = interest;
        notify(String.format("Updating debit interest\n New interest is %.3f", interest));
    }

    public void setDepositInterest(double interest) {
        depositInterest = interest;
        notify(String.format("Updating deposit interest\n New interest is %.3f", interest));
    }

    public void setCreditCommission(double interest)
    {
        creditCommission = interest;
        notify(String.format("Updating credit interest\n New interest is %.3f", interest));
    }

    public Optional<Account> getAccountById(int id)
    {
        return accounts.stream().filter(x -> x.getId() == id).findFirst();
    }

    public Optional<Client> getClientById(int id)
    {
        return clients.stream().filter(x -> x.getId() == id).findFirst();
    }

    @Override
    public void dayRecalculate() throws InvalidAmountException
    {
        for (Account account : accounts) account.increaseHideAmount();
    }

    @Override
    public void amountRecalculate()
    {
        for (Account account: accounts) account.approveHideAmount();
    }

    public void cancelOperation(int transactionId, int accountId)
            throws InvalidAccountIdException, InvalidTransactionId
    {
        Optional<Account> account;

        if ((account = getAccountById(accountId)).isEmpty())
            throw new InvalidAccountIdException("Invalid account id in cancellation");

        if (!account.get().cancellation(transactionId))
            throw new InvalidTransactionId(String.format("Invalid transaction id in bankId -> %d", bankId));
    }

    @Override
    public void subscribe(int subscriberId) throws InvalidClientIdException {
        Optional<Client> client = getClientById(subscriberId);
        if (client.isEmpty())
            throw new InvalidClientIdException(String.format("Invalid id %d", subscriberId));

        subscribers.add((Subscriber) client.get());
    }


    @Override
    public void unsubscribe(int subscriberId) throws InvalidClientIdException {
        Optional<Client> client = getClientById(subscriberId);
        if (client.isEmpty())
            throw new InvalidClientIdException(String.format("Invalid id %d", subscriberId));

        subscribers.remove((Subscriber) client.get());
    }

    @Override
    public void notify(String message) {
        subscribers.forEach(subscriber -> subscriber.update(message));
    }
}
