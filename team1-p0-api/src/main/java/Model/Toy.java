package Model;

public class Toy {

    private int toy_id,quantity;
    private String toyName;

    //Kinda confused why was this a final static?
    //Cost isn't fixed. There's sales and each toy is supposed to have a different cost.
    //    static final int cost = 10;
    //Changing to public for now. Ideally it's private.
    private int cost;

    static final int def_cost = 50;

    public Toy() {}

    public Toy(String toyName, int quantity ) {
        this.quantity = quantity;
        this.toyName = toyName;
        this.cost = def_cost;
    }

    public Toy(int toy_id, String toyName,int quantity) {
        this.quantity = quantity;
        this.toyName = toyName;
        this.toy_id = toy_id;
        this.cost = def_cost;
    }
    public Toy(int toy_id, String toyName,int quantity, int cost) {
        this.quantity = quantity;
        this.toyName = toyName;
        this.toy_id = toy_id;
        this.cost = cost;
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }


    @Override
    public String toString() {
        if(toy_id==0){
            return "Toy{" +
                    "toyName='" + toyName +
                    ", quantity=" + quantity +
                    '\'' +
                    '}';
        }
        return "Toy{" +
                "toy_id=" + toy_id +
                ", quantity=" + quantity +
                ", toyName='" + toyName + '\'' +
                '}';
    }
}
