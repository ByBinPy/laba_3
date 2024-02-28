package org.example.bank;

import jdk.jshell.spi.ExecutionControl;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.example.clients.Client;
import org.example.account.*;
import org.example.clients.Client;
import org.example.exceptions.InvalidAccountIdException;
import org.example.exceptions.InvalidTransactionId;
import org.example.exceptions.InvalidTransferAmountException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class Bank {
    private final int bankId;
    private double debitInterest;
    private double depositInterest;
    private double creditCommission;
    private final List<Client> clients;
    private final List<Account> accounts;

    public Bank(int id, double debit, double deposit, double credit) {
        bankId = id;
        debitInterest = debit;
        depositInterest = deposit;
        creditCommission = credit;
        clients = new ArrayList<>();
        accounts = new ArrayList<>();
    }

    public void registrationClient(@NonNull String name, @NonNull String surname, String address, int passportNumber) {
        if (clients.isEmpty())
            clients.add(
                    Client.builder()
                            .id(1)
                            .name(name)
                            .surname(surname)
                            .address(address)
                            .passportNumber(passportNumber)
                            .build());
        else
            clients.add(
                    Client.builder()
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
            case Debit -> addedAccount = new DebitAccount(id, clientId);
            case Credit -> addedAccount = new CreditAccount(id, clientId);
            case Deposit -> addedAccount = new DepositAccount(id, clientId);
            default -> throw new ExecutionControl.NotImplementedException(" ");
        }
        accounts.add(addedAccount);
    }
    public void setDebitInterest(double interest)
    {
        debitInterest = interest;
    }

    public void setDepositInterest(double interest) {
        depositInterest = interest;
    }

    public void setCreditCommission(double interest) {
        creditCommission = interest;
    }
    public void notifyClients(String notification)
    {

    }

    public Optional<Account> findAccountById(int id)
    {
        return accounts.stream().filter(x -> x.getId() == id).findFirst();
    }

    public Optional<Client> findClientById(int id)
    {
        return clients.stream().filter(x -> x.getId() == id).findFirst();
    }
    public void cancelOperation(int transactionId, int accountId)
            throws InvalidAccountIdException, InvalidTransactionId
    {
        Optional<Account> account;

        if ((account = findAccountById(accountId)).isEmpty())
            throw new InvalidAccountIdException("Invalid account id in cancellation");

        if (!account.get().cancellation(transactionId))
            throw new InvalidTransactionId(String.format("Invalid transaction id in bankId -> %d", bankId));
    }
}
