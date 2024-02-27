package org.example.bank;

import lombok.NonNull;
import org.example.account.Account;
import org.example.exceptions.*;

import java.util.ArrayList;
import java.util.Optional;

public class CentralBank {
    private final ArrayList<Bank> banks;
    private static final CentralBank centralBank = new CentralBank();

    private CentralBank()
    {
        banks = new ArrayList<Bank>();
    };

    public CentralBank getInstance() {
        return centralBank;
    }

    public void transfer(int fromBankId, int toBankId, int fromAccountId, int toAccountId, double diff_amount) throws InvalidAccountIdException
    {
        Optional<Account> fromAccount = centralBank.findBankByID(fromBankId).findAccountById(fromAccountId);
        Optional<Account> toAccount = centralBank.findBankByID(toBankId).findAccountById(toAccountId);
        if (fromAccount.isPresent() && toAccount.isPresent())
        {
            try
            {
                fromAccount.get().transfer(-diff_amount);
                toAccount.get().transfer(diff_amount);
            }
            catch (InvalidTransferAmountException e)
            {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else
        {
            throw new InvalidAccountIdException("wrong identifying data");
        }

    }

    public Bank registration(double debitInterest, double depositInterest, double creditInterest) throws InvalidDataForRegistrationBankException {
        if (!(debitInterest > 0 && depositInterest > 0 && creditInterest > 0))
            throw new InvalidDataForRegistrationBankException("incorrect data");
        if (banks.size() == 0)
            return new Bank(1, debitInterest, depositInterest, creditInterest);
        else
            return new Bank(banks.getLast().getBankId() + 1, debitInterest, depositInterest, creditInterest);
    }
    public void canselTransfer(int fromBankId, int toBankId,
                               int fromAccountId, int toAccountId,
                               int fromOperationId, int toOperationId)
            throws InvalidAccountIdException, InvalidTransactionId, InvalidBankIdException
    {

        Optional<Bank> bankFrom = banks.stream().filter(b -> b.getBankId() == fromBankId).findFirst();
        Optional<Bank> bankTo = banks.stream().filter(b -> b.getBankId() == toBankId).findFirst();

        if (bankFrom.isEmpty() || bankTo.isEmpty())
            throw new InvalidBankIdException(String.format("Invalid bankId %d %d in cancellation transfer", fromBankId, toBankId));

        bankTo.get().cancelOperation(toAccountId, toOperationId);
        bankFrom.get().cancelOperation(fromAccountId, fromOperationId);
    }
    private Bank findBankByID(int id)
    {
        Optional<Bank> bank = banks.stream().filter(b -> b.getBankId() == id ).findFirst();

        return bank.orElseGet(() -> new Bank(-1, -1, -1, -1));
    }
}