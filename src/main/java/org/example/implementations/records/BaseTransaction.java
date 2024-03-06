package org.example.implementations.records;

import org.example.declarations.Transaction;

/**
 * Record for logging bank operations
 */
public class BaseTransaction extends Transaction
{
    public BaseTransaction(int id, int day, double differenceAmount) {
        super(id, day, differenceAmount);
    }
    public void method()
    {
        BaseTransaction k = new BaseTransaction(1,1,1.0);
    }
}
