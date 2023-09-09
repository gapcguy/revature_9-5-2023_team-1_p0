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

    //covered
    // 0 usage. -- Remove if tests reveal it's not used.
    public List<Toy> getToys(){
        return toyDAO.getAvailableToys();
    }

    // 0 usage. -- Remove if tests reveal it's not used.
    public Toy getToyByID(int id){
        return toyDAO.getToyByID(id);
    }

    // 0 usage. -- Remove if tests reveal it's not used.
    public List<Toy> getToysForAccount(Account account){
        return transactionDAO.myToys(account);
    }

    // 0 usage. -- Remove if tests reveal it's not used.
    public boolean tryPull(){
        return false;
    }



}
