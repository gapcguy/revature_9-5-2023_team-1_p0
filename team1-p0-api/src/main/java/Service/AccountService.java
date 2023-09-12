package Service;


import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
    public final static int minLength = 4;
    AccountDAO accountDAO;
    public AccountService() { accountDAO = new AccountDAO(); }

    //Needs testing
    public Account createAccount(Account account) throws Exception {
        if (account.getUsername().length() < minLength || account.getPassword().length() < minLength) throw new Exception("Username or Password too short");
        Account daoValue = accountDAO.createAccount(account);
        if(daoValue != null){
            return daoValue;
        } else throw new Exception("account couldn't be created");
    }

    //covered
    public Account getUserAccount(Account account) throws Exception {

        Account daoValue =  accountDAO.getUserAccount(account);
        if (daoValue == null) throw new Exception("account not found");
        return daoValue;
    }


    //covered
    public int deposit(Account account, int amountToAdd) throws Exception {
        if(amountToAdd<0) throw new Exception("Cannot deposit negative amount");
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
