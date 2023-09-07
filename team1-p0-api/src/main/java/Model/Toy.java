package Model;

public class Toy {

    public int toy_id,quantity;
    public String toyName;
    //Kinda confused why was this a final static?
    //Cost isn't fixed. There's sales and each toy is supposed to have a different cost.
    //    static final int cost = 10;
    //Changing to public for now. Ideally it's private.
    public int cost;

    static final int def_cost = 50;

    public Toy() {}

    public Toy(String toyName, int quantity ) {
        this.quantity = quantity;
        this.toyName = toyName;
        cost.
    }

    public Toy(int toy_id, String toyName,int quantity) {
        this.quantity = quantity;
        this.toyName = toyName;
        this.toy_id = toy_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getToy_id() {
        return toy_id;
    }

    public String getToyName() {
        return toyName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Toy{" +
                "toy_id=" + toy_id +
                ", quantity=" + quantity +
                ", toyName='" + toyName + '\'' +
                '}';
    }
}
