package org.example.declarations;

import jdk.jshell.spi.ExecutionControl;
import lombok.NonNull;
import org.example.exceptions.*;
import org.example.implementations.accounts.AccountType;

import java.util.Optional;

/**
 * Declaration API for bank operations
 */
public interface Bank
{
    int getBankId();
    void registrationClient(@NonNull String name, @NonNull String surname, String address, int passportNumber);
    void registrationAccount(int clientId, AccountType type) throws ExecutionControl.NotImplementedException;
    void removeAccount(int accountId);
    void transfer(int fromBankId, int toBankId,int toAccountId, double transferAmount)
            throws InvalidBankIdException,
            InvalidAccountIdException,
            InvalidRefillAmountException,
            InvalidTransferAmountException;
    void setDebitInterest(double interest);
    void setDepositInterest(double interest);
    void setCreditCommission(double interest);
    Optional<Account> getAccountById(int id);
    Optional<Client> getClientById(int id);
    void dayRecalculate() throws InvalidAmountException;
    void amountRecalculate();

    void canselTransfer(int transactionId, int accountId) throws InvalidTransactionIdException, InvalidAccountIdException;

    void cancelOperation(int accountId, int transactionId)
            throws InvalidAccountIdException,
            InvalidTransactionIdException;
}
