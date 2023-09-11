package Service;


import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
    public final static int minLength = 4;
    AccountDAO accountDAO;
    public AccountService() { accountDAO = new AccountDAO(); }

    //Needs testing
    public Account createAccount(Account account) {
        if (account.getUsername().length() < minLength || account.getPassword().length() < minLength) return null;
        return accountDAO.createAccount(account);
    }

    //covered
    public Account getUserAccount(Account account) { return accountDAO.getUserAccount(account); }


    //covered
    public Account deposit(Account account, int amountToAdd) {
        Account userAccount = new Account(account.getUsername(), account.getPassword());
        boolean success = accountDAO.increaseCoinBalance(userAccount, amountToAdd);

        if (success) {
            System.out.println("Coin balance increased successfully.");
        } else {
            System.out.println("Failed to increase coin balance.");
        }
        return null;
    }

    public Account deleteAccount(String username){
        return accountDAO.deleteAccountByName(username);
    }

    public List<Account> getAllAccounts(){
        return accountDAO.getAllUsers();
    }
}
