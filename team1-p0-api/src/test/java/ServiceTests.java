import Model.Account;
import Model.Toy;
import Model.Transaction;
import Service.AccountService;
import Service.ToyService;
import Service.TransactionService;
import Utils.ConnectionUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertThrows;

public class ServiceTests {

    @Before
    public void setUp() throws Exception {
        ConnectionUtil.resetTestDatabase();
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
        assertThrows(Exception.class,()->as.deposit(b, -100));

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
        Account a = new Account("user2", "reston");
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
        Account a = as.getUserAccount(new Account("user3", "morgantown"));
        int testSize = ts.getToysForAccountID(a.getAccount_id()).size();
        assert(testSize == 0);

        ts.pull(a);
        List<Toy> tl = ts.getToysForAccountID(a.getAccount_id());
        assert(!tl.isEmpty());
    }
}
