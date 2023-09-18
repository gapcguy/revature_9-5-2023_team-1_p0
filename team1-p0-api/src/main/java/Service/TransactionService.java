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
        this.accountDAO     = new AccountDAO();
        this.transactionDAO = new TransactionDAO();
        this.toyDAO         = new ToyDAO();
    }

    public Transaction pull(Account account) throws Exception {
        AccountService as = new AccountService();
        Toy     newToy  = toyDAO.chooseRandomToy();
        if(as.getUserAccount(account).getCoinBalance() < newToy.getCost() ) {
            throw new Exception("insufficient funds");
        }

        boolean decr = accountDAO.decreaseCoinBalance(account, newToy.getCost());

        if(!decr) {
            throw new Exception("unable to withdraw");
        }

        boolean working = toyDAO.decrementQuantity(newToy);

        if(!working){
            throw new Exception("not able to pull toy :(");
        }

        Transaction curr = new Transaction(account.getAccount_id(), newToy.getToy_id(), newToy.getToyName());

        curr = transactionDAO.addTransaction(curr);

        if(curr == null) {
            throw new Exception("not able to add transaction");
        }

        return curr;
    }

    public List<Toy> getToysForAccount(Account account) throws Exception {
       return transactionDAO.myToys(account);
    }

    public List<Toy> getToysForAccountID(int id) throws Exception {
        List<Toy> toys = transactionDAO.getToysFromAccountId(id);
        if (toys == null || toys.isEmpty()) throw new Exception("No toys found");
        return toys;
    }

    public int getNumberOfToysForAccountID(int id) throws Exception {
        List<Toy> toys = getToysForAccountID(id);
        if(toys.isEmpty()){
            return 0;
        }
        int total = 0;
        for(int i = 0; i < toys.size(); i++){
            total += toys.get(i).getQuantity();
        }
        return total;
    }
}
