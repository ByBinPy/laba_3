package org.example.imp.bank;

import org.example.declarations.Bank;
import org.example.exceptions.*;

import java.util.ArrayList;
import java.util.Optional;

public class CentralBank {
    private final ArrayList<Bank> banks = new ArrayList<Bank>();
    private static final CentralBank centralBank = new CentralBank();


    public static CentralBank getInstance() {
        return centralBank;
    }


    public BankImp registration(double debitInterest, double depositInterest, double creditInterest) throws InvalidDataForRegistrationBankException {
        if (!(debitInterest > 0 && depositInterest > 0 && creditInterest > 0))
            throw new InvalidDataForRegistrationBankException("incorrect data");
        if (banks.size() == 0)
            return new BankImp(1, debitInterest, depositInterest, creditInterest);
        else
            return new BankImp(banks.getLast().getBankId() + 1, debitInterest, depositInterest, creditInterest);
    }
    public Optional<Bank> getBankByID(int id)
    {
        return banks.stream().filter(b -> b.getBankId() == id ).findFirst();
    }
    public void notifyBanksAboutNewDay() throws InvalidAmountException
    {
        for (Bank bank : banks) bank.dayRecalculate();
    }
    public void notifyBanksAboutAccrual() throws InvalidAmountException
    {
        for (Bank bank : banks) bank.amountRecalculate();
    }
}