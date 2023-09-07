package Model;

public class Transaction {

<<<<<<< Updated upstream
    private int transaction_id,account_id,toy_id;
    private String toyName;
    static final int pullCost = 10;
=======
    public int getTransaction_id() {
        return transaction_id;
    }

    private int transaction_id;

    public int getAccount_id() {
        return account_id;
    }

    private int account_id;

    public int getToy_id() {
        return toy_id;
    }

    private int toy_id;
>>>>>>> Stashed changes

    public Transaction(int transaction_id,int account_id,int toy_id, String toyName){
        this.transaction_id = transaction_id;
        this.account_id = account_id;
        this.toy_id = toy_id;
        this.toyName = toyName;

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

    public void setToyName(String toyName) {
        this.toyName = toyName;
    }

    public String getToyName() {
        return toyName;
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
