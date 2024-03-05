package org.example.imp.service;

import org.example.exceptions.InvalidAmountException;
import org.example.imp.bank.CentralBank;

public class Ticker
{
    private int day = 0;
    private static Ticker ticker;

    public static Ticker getTicker()
    {
        if (ticker == null)
            ticker = new Ticker();
        return ticker;
    }

    public void moveDay()
    {
        day++;
        try {
            CentralBank.getInstance().notifyBanksAboutNewDay();
        } catch (InvalidAmountException e) {
            throw new RuntimeException(e);
        }
    }
    public void moveDay(int countDays)
    {
        for (int i = 0; i < countDays; i++) {
            moveDay();
        }
    }
}
