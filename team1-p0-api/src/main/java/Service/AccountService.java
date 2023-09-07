package Service;


import Model.Account;
import DAO.AccountDAO;

public class AccountService {

    AccountDAO accountDAO;
    public AccountService() { accountDAO = new AccountDAO(); }

    //Needs testing
    public Account createAccount(Account account) {
        if (account.getUsername().length() <1 || account.getPassword().length() <1) return null;
        return accountDAO.createAccount(account);
    }

    //covered
    public Account getUserAccount(Account account) { return accountDAO.getUserAccount(account); }


    //covered
    public Account changeCoinBalance(Account account, int amountToAdd) {
        AccountDAO accountDAO = new AccountDAO();
        Account userAccount = new Account(account.getUsername(), account.getPassword());
        boolean success = accountDAO.increaseCoinBalance(userAccount, amountToAdd);

        if (success) {
            System.out.println("Coin balance increased successfully.");
        } else {
            System.out.println("Failed to increase coin balance.");
        }
        return null;
    }
}
