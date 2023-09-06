package Service;


import Model.Account;
import DAO.AccountDAO;

public class AccountService {

    AccountDAO accountDAO;
    public AccountService() { accountDAO = new AccountDAO(); }
    public Account createAccount(Account account) { return accountDAO.createAccount(account); }
    public Account login(Account account) { return accountDAO.getUserAccount(account); }
}
