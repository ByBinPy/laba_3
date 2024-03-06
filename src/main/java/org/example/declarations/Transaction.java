package org.example.declarations;

import lombok.Getter;

/**
 * Record for logging bank operations
 */
public abstract class Transaction
{
    public final int id;
    public final int day;
    public final double differenceAmount;
    protected Transaction(int id, int day, double differenceAmount) {
        this.id = id;
        this.day = day;
        this.differenceAmount = differenceAmount;
    }
}
