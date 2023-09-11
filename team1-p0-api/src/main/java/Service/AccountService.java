package Service;


import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {

    AccountDAO accountDAO;
    public AccountService() { accountDAO = new AccountDAO(); }

    //Needs testing
    public Account createAccount(Account account) throws Exception {
        if (account.getUsername().isEmpty() || account.getPassword().isEmpty()) throw new Exception("Username and Password cannot be empty");
        return accountDAO.createAccount(account);
    }

    //covered
    public Account getUserAccount(Account account) { return accountDAO.getUserAccount(account); }


    //covered
    public int deposit(Account account, int amountToAdd) throws Exception {
        if(amountToAdd<0) throw new Exception("Cannot deposit negative ammount");
        Account userAccount = new Account(account.getUsername(), account.getPassword());
        boolean success = accountDAO.increaseCoinBalance(userAccount, amountToAdd);
        if(!success) throw new Exception("Transaction Unable to Complete");
        return account.getCoinBalance() + amountToAdd;
    }

    public Account deleteAccount(String username){
        return accountDAO.deleteAccountByName(username);
    }

    public List<Account> getAllAccounts() throws Exception {

        List accounts = accountDAO.getAllUsers();
        if(accounts == null || accounts.isEmpty()) throw new Exception("no accounts found");
        return accounts;
    }
}
