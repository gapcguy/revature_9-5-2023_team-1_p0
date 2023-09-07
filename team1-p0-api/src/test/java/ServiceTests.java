import Model.Account;
import Service.AccountService;
import Utils.Application;
import org.checkerframework.checker.units.qual.A;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class ServiceTests {

    @Before
    public void setUp() {
        Application.databaseSetup();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void checkGetUserAccount(){
        AccountService as = new AccountService();
        Account a = new Account("user1", "dallas");
        Account b = as.getUserAccount(a);
        assert(a.getUsername().equals(b.getUsername()));
    }

    @Test
    public void addCurrency(){
        AccountService as = new AccountService();
        Account a = new Account("user1", "dallas");
        Account b = as.getUserAccount(a);
        as.addToCoinBalance(b, -100);
        a = as.getUserAccount(b);
        assert(b.getCoinBalance()-100 == a.getCoinBalance());

    }
}
