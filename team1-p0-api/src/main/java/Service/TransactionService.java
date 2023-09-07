package Service;

import Model.Account;
import Model.Toy;
import Model.Transaction;
import DAO.*;

import java.util.List;

public class TransactionService {
    AccountDAO accountDAO;
    ToyDAO toyDAO;
    TransactionDAO transactionDAO;


    public TransactionService(){
        this.accountDAO = new AccountDAO();
        this.transactionDAO = new TransactionDAO();
        this.toyDAO = new ToyDAO();
    }

    public Transaction pull(Account account) throws Exception {
        if(account.getCoinBalance()<Transaction.getPullCost()){ throw new Exception("insufficient funds");};
        Boolean decr = accountDAO.decreaseCoinBalance(account,Transaction.getPullCost());
        if(!decr)throw new Exception("unable to withdraw");
        Toy newToy = toyDAO.chooseRandomToy();
        boolean working = toyDAO.decrementQuantity(newToy);
        if(!working){ throw new Exception("not able to pull toy :(");};
        Transaction curr = new Transaction(account.getAccount_id(),newToy.getToy_id(),newToy.getToyName());
        curr = transactionDAO.addTransaction(curr);
        if(curr == null){ throw new Exception("not able to add transaction");};

        return curr;
    }

    public List<Toy> getToysForAccount(Account account){
        return transactionDAO.myToys(account);
    }
}
