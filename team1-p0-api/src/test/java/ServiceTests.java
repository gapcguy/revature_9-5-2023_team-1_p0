import Model.Account;
import Model.Toy;
import Model.Transaction;
import Service.AccountService;
import Service.ToyService;
import Service.TransactionService;

import Utils.ConnectionUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


public class ServiceTests {

    @Before
    public void setUp() {
        try {
            ConnectionUtil.resetTestDatabase();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testCheckGetUserAccount(){
        AccountService as = new AccountService();
        Account a = new Account("user1", "dallas");
        Account b = as.getUserAccount(a);
        assert(a.getUsername().equals(b.getUsername()));
    }

    @Test
    public void testAddCurrency() throws Exception {
        AccountService as = new AccountService();
        Account a = new Account("user1", "dallas");
        Account b = as.getUserAccount(a);
        int before = b.getCoinBalance();
        int deposit = as.deposit(b, 100);
        a = as.getUserAccount(b);
        assert(before +100 == a.getCoinBalance());
        assertEquals(deposit, 150);
    }

    @Test
    public void testAddCurrencyFailOnNeg() {
        AccountService as = new AccountService();
        Account        a  = new Account("user1", "dallas");
/*        Account b = as.getUserAccount(a);
        assertThrows(Exception.class,()->as.deposit(b, -100));
*/
        Exception exception = assertThrows(Exception.class, () -> as.deposit(a, -100));
        assertEquals("Cannot deposit negative amount", exception.getMessage());
    }

    @Test
    public void testGetAvailableToys(){
        ToyService toyService = new ToyService();
        List<Toy> toys = toyService.getAvailableToys();
        assertEquals(4,toys.size());

    }

    @Test
    public void testPull() throws Exception {
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
    public void testDeleteUser(){
        AccountService as = new AccountService();
        Account a = as.getUserAccount(new Account("user1", "dallas"));
        as.deleteAccount(a.getUsername());
        a = as.getUserAccount(a);
        assert(a==null);
    }

    @Test
    public void testGetToys() throws Exception {
        AccountService as = new AccountService();
        TransactionService ts = new TransactionService();
        int testSize = 0;
        try {
            testSize = ts.getToysForAccountID(1).size();
        } catch(Exception e){
            e.printStackTrace();
        }
        assert(testSize == 0);
        Account a = as.getUserAccount(new Account("user3", "morgantown"));
        ts.pull(a);
        List<Toy> tl = ts.getToysForAccountID(a.getAccount_id());
        assert(!tl.isEmpty());
    }
}
