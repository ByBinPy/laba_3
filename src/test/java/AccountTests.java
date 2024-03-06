import jdk.jshell.spi.ExecutionControl;
import lombok.SneakyThrows;
import org.example.declarations.Account;
import org.example.declarations.Bank;
import static org.mockito.Mockito.*;
import org.example.exceptions.InvalidTransferAmountException;
import org.example.implementations.services.Ticker;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.example.exceptions.InvalidDataForRegistrationBankException;
import org.example.exceptions.InvalidRefillAmountException;
import org.example.implementations.accounts.AccountType;
import org.example.implementations.banks.CentralBank;

import java.util.Optional;
import java.util.Timer;

public class AccountTests
{
    Bank bank;
    CentralBank centralBank;
    @BeforeEach
    public void setup() {
        centralBank = CentralBank.getInstance();
        try {
            centralBank.registration(10, 12.5, 5);
            centralBank.registration(7.5, 10.5, 6);
            centralBank.registration(1, 2, 3);
        }
        catch (InvalidDataForRegistrationBankException e) {
            throw new RuntimeException(e);
        }
        bank = centralBank.getBankByID(1).get();
        bank.registrationClient("S", "I", "ITMO", 2);
        try {
            bank.registrationAccount(1, AccountType.Debit);
            bank.registrationAccount(1, AccountType.Credit);
            bank.registrationAccount(1,AccountType.Deposit);
        } catch (ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }
    @AfterEach
    public void clear()
    {
        for (int id = 1; id < 4; id++)
            centralBank.depriveOfLicense(id);

        for (int id = 1; id < 4; id++)
            bank.removeAccount(id);
    }
    @Order(1)
    @Test
    public void tryRefillWithNegativeAmountInDeposit() {
        try {
            bank.getAccountById(1).get().refill(-100);
            Assertions.fail();
        }
        catch (InvalidRefillAmountException e)
        {
            Assertions.assertTrue(true);
        }
    }
    @Order(2)
    @Test
    public void tryWithdrawalAmountGreatThenCurrentAmountInDebitAccount()
    {
        try{
            bank.getAccountById(1).get().withdrawal(10);
            Assertions.fail();
        } catch (InvalidTransferAmountException e) {
            Assertions.assertTrue(true);
        }
    }
    @Order(3)
    @Test
    public void tryWithdrawalAmountGreatThenCurrentAmountInDepositAccount()
    {
        try{
            bank.getAccountById(3).get().withdrawal(10);
            Assertions.fail();
        } catch (InvalidTransferAmountException e) {
            Assertions.assertTrue(true);
        }
    }
    @Order(4)
    @SneakyThrows
    @Test
    public void tryWithdrawalAmountGreatThenCurrentAmountInCreditAccount() {
        bank.getAccountById(2).get().withdrawal(100);
        Assertions.assertEquals(-100, bank.getAccountById(2).get().getAmount());
    }
    @SneakyThrows
    @Order(5)
    @Test
    public void checkDayCalculateInDebit()
    {
        bank.getAccountById(1).get().refill(100);
        Ticker.getTicker().moveDay();
        Assertions.assertEquals(10.0/365, bank.getAccountById(1).get().getHideDifferenceAmount());
    }
    @Order(6)
    @SneakyThrows
    @Test
    public void checkDayCalculateInCredit()
    {
        bank.getAccountById(2).get().withdrawal(100);
        Ticker.getTicker().moveDay();
        Assertions.assertEquals(-5.0/365, bank.getAccountById(2).get().getHideDifferenceAmount());
    }
    @Order(7)
    @SneakyThrows
    @Test
    public void checkDayCalculateInDeposit()
    {
        bank.getAccountById(3).get().refill(100);
        Ticker.getTicker().moveDay();
        Assertions.assertEquals(12.5/365, bank.getAccountById(3).get().getHideDifferenceAmount());
    }
    @SneakyThrows
    @Order(8)
    @Test
    public void checkApproveInDebit()
    {
        bank.getAccountById(1).get().refill(100);
        Ticker.getTicker().moveDay();
        centralBank.notifyBanksAboutAccrual();
        Assertions.assertEquals(100+10.0/365, bank.getAccountById(1).get().getAmount());
    }
    @SneakyThrows
    @Order(9)
    @Test
    public void checkApproveInCredit()
    {
        bank.getAccountById(2).get().withdrawal(100);
        Ticker.getTicker().moveDay();
        centralBank.notifyBanksAboutAccrual();
        Assertions.assertEquals(-100-5.0/365, bank.getAccountById(2).get().getAmount());
    }
    @SneakyThrows
    @Order(10)
    @Test
    public void checkApproveInDeposit()
    {
        bank.getAccountById(3).get().refill(100);
        Ticker.getTicker().moveDay();
        centralBank.notifyBanksAboutAccrual();
        Assertions.assertEquals(100+12.5/365, bank.getAccountById(3).get().getAmount());
    }
    @SneakyThrows
    @Order(11)
    @Test
    public void checkTimeLapse()
    {
        bank.getAccountById(1).get().refill(100);
        Ticker.getTicker().moveDay(3);
        Assertions.assertEquals(100*0.1*3/365, bank.getAccountById(1).get().getHideDifferenceAmount());
    }
    @SneakyThrows
    @Order(12)
    @Test
    public void checkClientNotifications() {
        bank.setCreditCommission(20);

    }
}
