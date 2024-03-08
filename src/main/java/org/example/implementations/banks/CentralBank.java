package org.example.implementations.banks;
import org.example.declarations.Bank;
import org.example.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Head of all banks
 * class where banks are stored banks
 * it notifier banks for accrual and day-updating
 */
public class CentralBank {
    private final List<Bank> banks = new ArrayList<>();
    private static final CentralBank centralBank = new CentralBank();


    public static CentralBank getInstance() {
        return centralBank;
    }


    public void registration(double debitInterest, double depositInterest, double creditInterest) throws InvalidDataForRegistrationBankException {
        if (!(debitInterest > 0 && depositInterest > 0 && creditInterest > 0))
            throw new InvalidDataForRegistrationBankException("incorrect data");
        if (banks.size() == 0)
            banks.add(new BankImp(1, debitInterest/100, depositInterest/100, creditInterest/100));
        else
            banks.add(new BankImp(banks.getLast().getBankId() + 1, debitInterest, depositInterest, creditInterest));
    }
    public void depriveOfLicense(int bankId) throws InvalidBankIdException
    {
        Optional<Bank> bank = getBankByID(bankId);
        if (bank.isEmpty())
            throw new InvalidBankIdException("Invalid bankId in depriving");

        banks.remove(bank.get());
    }
    public Optional<Bank> getBankByID(int id)
    {
        return banks.stream().filter(b -> b.getBankId() == id ).findFirst();
    }
    public void notifyBanksAboutNewDay() throws InvalidAmountException
    {
        for (Bank bank : banks) bank.dayRecalculate();
    }
    public void notifyBanksAboutAccrual() {
        for (Bank bank : banks) bank.amountRecalculate();
    }
}