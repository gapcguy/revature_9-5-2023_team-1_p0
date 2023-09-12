import Model.Account;
import Service.AccountService;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class CAccountServiceTest {

    private AccountService accountService;

    @Before
    public void setUp() {
        accountService = new AccountService();
    }

    @Test
    public void testTransactionUnableToComplete() throws Exception {
        Account a = new Account("user1", "dallas");

        // Use reflection to access the private increaseCoinBalance method
        Method method = AccountService.class.getDeclaredMethod("deposit", Account.class, int.class);
        method.setAccessible(true);

        try {
            method.invoke(accountService, a, +100);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            assertEquals("Transaction Unable to Complete", cause.getMessage());
        }
    }

    @Test
    public void testTransactionCannotDepositNegative() throws Exception {
        Account a = new Account("user1", "dallas");

        // Use reflection to access the private increaseCoinBalance method
        Method method = AccountService.class.getDeclaredMethod("deposit", Account.class, int.class);
        method.setAccessible(true);

        try {
            method.invoke(accountService, a, -100);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            assertEquals("Cannot deposit negative amount", cause.getMessage());
        }
    }
}
