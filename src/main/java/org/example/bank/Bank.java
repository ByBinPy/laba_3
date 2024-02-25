package org.example.bank;

import lombok.Getter;
import lombok.Setter;
import org.example.Client;
import org.example.account.Account;

import java.util.List;

@Getter
public class Bank {
    private int bankId;
    private double debitInterest;
    private double depositInterest;
    private double creditCommission;
    private List<Client> clients;
    private List<Account> accounts;

    public Bank(int id, double debit, double deposit, double credit) {
        bankId = id;
        debitInterest = debit;
        depositInterest = deposit;
        creditCommission = credit;
    }

    public void setDebitInterest(double interest) {
        debitInterest = interest;
    }

    public void setDepositInterest(double interest) {
        depositInterest = interest;
    }

    public void setCreditCommission(double interest) {
        creditCommission = interest;
    }

    public Account findAccountById(int id) {
        if (accounts.isEmpty()) {
            return null;
        }

        int right = accounts.size() - 1;
        int left = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int idSearch = accounts.get(mid).getId();

            if (id == idSearch) {
                return accounts.get(mid);
            }

            if (idSearch < id) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return null;
    }

    public Client findClientById(int id) {
        if (clients.isEmpty()) {
            return null;
        }

        int right = clients.size() - 1;
        int left = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int idSearch = clients.get(mid).getId();

            if (id == idSearch) {
                return clients.get(mid);
            }

            if (idSearch < id) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }
}
