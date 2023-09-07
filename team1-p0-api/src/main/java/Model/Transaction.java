package Model;

public class Transaction {

    int transaction_id,account_id,toy_id;
    static final int pullCost = 10;

    public Transaction(int transaction_id,int account_id,int toy_id){
        this.transaction_id = transaction_id;
        this.account_id = account_id;
        this.toy_id = toy_id;

    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public void setToy_id(int toy_id) {
        this.toy_id = toy_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public static int getPullCost() {
        return pullCost;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public int getToy_id() {
        return toy_id;
    }
}
