package Service;

import Model.Account;
import Model.Toy;
import Model.Transaction;
import DAO.*;

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
        accountDAO.decreaseCoinBalance(account,Transaction.getPullCost());
        Toy newToy = toyDAO.chooseRandomToy();
        boolean working = toyDAO.decrementQuantity(newToy.getToy_id(), newToy.getQuantity());
        if(!working){ throw new Exception("not able to pull toy :(");};
        Transaction curr = new Transaction(account.getAccount_id(),newToy.getToy_id(),newToy.getToyName());
        if(curr == null){ throw new Exception("not able to add transaction");};

        return curr;
    }
}
