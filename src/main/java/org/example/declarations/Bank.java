package org.example.declarations;

import jdk.jshell.spi.ExecutionControl;
import lombok.NonNull;
import org.example.imp.account.AccountType;
import org.example.exceptions.InvalidAccountIdException;
import org.example.exceptions.InvalidBankIdException;
import org.example.exceptions.InvalidTransactionId;
import org.example.exceptions.InvalidTransferAmountException;

import java.util.Optional;

public interface Bank
{
    int getBankId();
    void registrationClient(@NonNull String name, @NonNull String surname, String address, int passportNumber);
    void registrationAccount(int clientId, AccountType type) throws ExecutionControl.NotImplementedException;
    void transfer(int toBankId,int toAccountId, double transferAmount)
            throws InvalidBankIdException,
            InvalidAccountIdException,
            InvalidTransferAmountException;
    void setDebitInterest(double interest);
    void setDepositInterest(double interest);
    void setCreditCommission(double interest);
    void notifyClients(String notification);
    Optional<Account> getAccountById(int id);
    Optional<Client> getClientById(int id);
    void cancelOperation(int transactionId, int accountId)
            throws InvalidAccountIdException,
            InvalidTransactionId;

}
