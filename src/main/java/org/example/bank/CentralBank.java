package org.example.bank;

import lombok.NonNull;
import org.example.account.Account;
import org.example.exceptions.InvalidAccountIdException;
import org.example.exceptions.InvalidDataForRegistrationBankException;
import org.example.exceptions.InvalidTransferAmountException;

import java.util.ArrayList;

public class CentralBank {
    private ArrayList<Bank> banks;
    private static CentralBank centralBank = new CentralBank();

    private CentralBank()
    {
        banks = new ArrayList<Bank>();
    };

    public CentralBank getInstance() {
        return centralBank;
    }

    public void transfer(int fromBankId, int toBankId, int fromAccountId, int toAccountId, double diff_amount) throws InvalidAccountIdException //сменить
    {
        Account fromAccount = centralBank.findBankByID(fromBankId).findAccountById(fromAccountId);
        Account toAccount = centralBank.findBankByID(toBankId).findAccountById(toAccountId);
        if (fromAccount != null && toAccount != null)
        {
            try
            {
                fromAccount.transfer(-diff_amount);
                toAccount.transfer(diff_amount);
            }
            catch (InvalidTransferAmountException e) //создать своё исключение
            {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else
        {
            throw new InvalidAccountIdException("wrong identifying data"); //создать своё исключение
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

    private @NonNull Bank findBankByID(int id) {

        if (banks.isEmpty()) {
            return new Bank(-1,-1,-1,-1);
        }

        int right = banks.size() - 1;
        int left = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int idSearch = banks.get(mid).getBankId();

            if (id == idSearch) {
                return banks.get(mid);
            }

            if (idSearch < id) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return new Bank(-1,-1,-1,-1);
    }
}