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
    public void addCurrency() throws Exception {
        AccountService as = new AccountService();
        Account a = new Account("user1", "dallas");
        Account b = as.getUserAccount(a);
        int before = b.getCoinBalance();
        as.deposit(b, 100);
        a = as.getUserAccount(b);
        assert(before +100 == a.getCoinBalance());

    }

    @Test
    public void addCurrencyFailOnNeg() throws Exception {
        AccountService as = new AccountService();
        Account a = new Account("user1", "dallas");
        Account b = as.getUserAccount(a);
        int test = 0;
        try {
            int _a = as.deposit(b, -100);
        } catch(Exception e) {
            test = 1;
        }
        assert(test == 1);

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

        int beforeSize = 0;
        try {
            beforeSize = transactionService.getToysForAccount(b).size();
        } catch(Exception e) {
            e.printStackTrace();
        }
        ToyService toyService = new ToyService();
        List<Toy> toys = toyService.getAvailableToys();
        Transaction trans = transactionService.pull(b);

        int afterBal = as.getUserAccount(a).getCoinBalance();
        int afterSize = transactionService.getToysForAccount(b).size();

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

    @Test
    public void getToysTest() throws Exception {
        AccountService as = new AccountService();
        TransactionService ts = new TransactionService();
        int testSize = 0;
        try {
            testSize = ts.getToysForAccountID(1).size();
        } catch(Exception e){
            e.printStackTrace();
        }
        assert(testSize == 0);
        ts.pull(as.getUserAccount(new Account("user1", "dallas")));
        List<Toy> tl = ts.getToysForAccountID(1);
        assert(!tl.isEmpty());
    }
}
