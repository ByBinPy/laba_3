package org.example.declarations;

import jdk.jshell.spi.ExecutionControl;
import lombok.NonNull;
import org.example.exceptions.*;
import org.example.imp.accounts.AccountType;

import java.util.Optional;

/**
 * Declaration API for bank operations
 */
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
    Optional<Account> getAccountById(int id);
    Optional<Client> getClientById(int id);
    void dayRecalculate() throws InvalidAmountException;
    void amountRecalculate();
    void cancelOperation(int transactionId, int accountId)
            throws InvalidAccountIdException,
            InvalidTransactionId;

}
