package Model;

public class Transaction {

    int transaction_id,account_id,toy_id;
    static final int cost = 10;

    public Transaction(int transaction_id,int account_id,int toy_id){
        this.transaction_id = transaction_id;
        this.account_id = account_id;
        this.toy_id = toy_id;

    }
}
