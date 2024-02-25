package org.example.account;

import org.example.Transaction;

import java.util.List;

public abstract class Account
{
    public abstract int getId();
    public abstract double transaction(double diffAmount) throws Exception;
}
