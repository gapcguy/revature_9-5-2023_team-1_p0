package Service;


import Model.Account;
import DAO.AccountDAO;

public class AccountService {

    AccountDAO accountDAO;
    public AccountService() { accountDAO = new AccountDAO(); }
    public Account createAccount(Account account) { return accountDAO.createAccount(account); }
    public Account getUserAccount(Account account) { return accountDAO.getUserAccount(account); }

    public Account addToCoinBalance(Account account) {
        AccountDAO accountDAO = new AccountDAO();
        Account userAccount = new Account(account.getUsername(), account.getPassword());
        int amountToAdd = 100;
        boolean success = accountDAO.increaseCoinBalance(userAccount, amountToAdd);

        if (success) {
            System.out.println("Coin balance increased successfully.");
        } else {
            System.out.println("Failed to increase coin balance.");
        }
        return null;
    }
}
