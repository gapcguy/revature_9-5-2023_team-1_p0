import Model.Account;
import Model.Toy;
import Model.Transaction;
import Service.AccountService;
import Service.ToyService;
import Service.TransactionService;
import Utils.Application;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

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
        as.changeCoinBalance(b, -100);
        a = as.getUserAccount(b);
        assert(b.getCoinBalance()-100 == a.getCoinBalance());

    }


    @Test
    public void getAvailableToys(){
        ToyService toyService = new ToyService();
        List<Toy> toys = toyService.getAvailableToys();
        Assert.assertEquals(4,toys.size());

    }

    @Test
    public void pull() throws Exception {
        TransactionService transactionService = new TransactionService();
        AccountService as = new AccountService();
        Account a = new Account("user1", "dallas");
        Account b = as.getUserAccount(a);

        int beforBal = b.getCoinBalance();
        int beforeSize = transactionService.getToysForAccount(b).size();

        ToyService toyService = new ToyService();
        List<Toy> toys = toyService.getAvailableToys();
        Transaction trans = transactionService.pull(b);

        int afterBal = as.getUserAccount(a).getCoinBalance();
        int afterSize = transactionService.getToysForAccount(b).size();
        System.out.println(beforBal);
        System.out.println(afterBal);

        assert(afterBal<beforBal);
        assert(afterSize>beforeSize);
    }

    @Test
    public void deleteTest(){
        AccountService as = new AccountService();
        Account a = as.getUserAccount(new Account("user1", "dallas"));
        as.deleteAccount(a.getUsername());
        a = as.getUserAccount(a);
        assert(a==null);
    }
}
