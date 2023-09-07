package Service;

import DAO.AccountDAO;
import DAO.ToyDAO;
import DAO.TransactionDAO;
import Model.Account;
import Model.Toy;
import Model.Transaction;

import java.util.List;

public class GatchaService {
    ToyDAO toyDAO;
    TransactionDAO transactionDAO;
    AccountDAO accountDAO;

    public GatchaService(){
        toyDAO = new ToyDAO();
        transactionDAO = new TransactionDAO();
        accountDAO = new AccountDAO();
    }

    public List<Toy> getToys(){
        return toyDAO.getAvailableToys();
    }

    public Toy getToyByID(int id){
        return toyDAO.getToyByID(id);
    }

    public List<Toy> getToysForAccount(Account account){
        return transactionDAO.myToys(account);
    }

    public boolean tryPull(){
        return false;
    }



}
