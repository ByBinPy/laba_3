package org.example.account;

import lombok.Getter;
import org.example.Transaction;
import org.example.exceptions.InvalidTransferAmountException;

import java.util.List;
@Getter
public class DebitAccount extends Account {
    int id;
    double amount;
    List<Transaction> transaction;

    @Override
    public double transaction(double diffAmount) throws InvalidTransferAmountException {
        if (diffAmount + amount < 0)
        {
            throw new InvalidTransferAmountException("result amount less 0");
        }
        amount += diffAmount;
        return amount;
    }
}
