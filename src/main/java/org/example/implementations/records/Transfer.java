package org.example.implementations.records;
import org.example.declarations.Transaction;

public class Transfer extends Transaction
{
    public final int externalAccountId;
    public final int externalBankId;
    public Transfer(int id, int day, double differenceAmount, int externalBankId, int externalAccountId) {
        super(id, day, differenceAmount);
        this.externalAccountId = externalAccountId;
        this.externalBankId = externalBankId;
    }
}
