package org.example.account;

import lombok.Getter;
import org.example.Transaction;
import org.example.exceptions.InvalidTransferAmountException;

import java.util.List;
public class DebitAccount extends Account {

    public DebitAccount(int id,int clientId) {
        super(id, clientId);
    }
    @Override
    public double withdrawal(double amount) throws InvalidTransferAmountException
    {

        if (this.amount - amount < 0)
            throw new InvalidTransferAmountException("try refill amount great then currency amount");
        this.amount -= amount;
        return this.amount;
    }
    @Override
    public double transfer(double transferAmount) throws InvalidTransferAmountException {
        if (transferAmount + amount < 0)
        {
            throw new InvalidTransferAmountException("result amount less 0");
        }
        amount += transferAmount;
        return amount;
    }
}
