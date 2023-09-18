package Model;

public class Toy {
    /* ------------------------------------------------
        privately-scoped variables.
       ------------------------------------------------ */
    private      String toyName, toyImage;
    private      int    quantity, cost, toy_id;

    static final int    def_cost = 50;

    /* ------------------------------------------------
        Constructors
       ------------------------------------------------ */
    public Toy() {}  // States no usages, but Used by the tests

    public Toy( String toyName, int quantity ) {
        this.quantity = quantity;
        this.toyName  = toyName;
        this.cost     = def_cost;
    }
    public Toy( String toyName, int quantity , int cost) {
        this.quantity = quantity;
        this.toyName  = toyName;
        this.cost     = cost;
    }

    public Toy(int toy_id, String toyName, int quantity) {
        this.quantity = quantity;
        this.toyName  = toyName;
        this.toy_id   = toy_id;
        this.cost     = def_cost;
    }
    public Toy(int toy_id, String toyName,int quantity, int cost) {
        this.quantity = quantity;
        this.toyName  = toyName;
        this.toy_id   = toy_id;
        this.cost     = cost;
    }

    public Toy(int toy_id, String toyName, int quantity, String toyImage) {
        this.toy_id = toy_id;
        this.toyName = toyName;
        this.quantity = quantity;
        this.toyImage = toyImage;
        this.cost = def_cost;
    }

    public Toy(int toy_id, String toyName, int quantity, int cost, String toyImage){
        this.toy_id = toy_id;
        this.toyName = toyName;
        this.quantity = quantity;
        this.toyImage = toyImage;
        this.cost = cost;
    }
    /* ------------------------------------------------
        Getters
       ------------------------------------------------ */
    public int    getQuantity() {
        return quantity;
    }
    public int    getToy_id  () {
        return toy_id;
    }
    public String getToyName () { return toyName; }
    public String getToyImage () {return toyImage; }
    public int  getCost    () { return cost;    }

    /* ------------------------------------------------
        Setters - Commented out due to lack of use.
       ------------------------------------------------ */
    //public void setQuantity(int quantity) { this.quantity = quantity; }
    //public void setCost    (int cost    ) { this.cost = cost; }


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
                ", toyName='" + toyName +
                ", quantity=" + quantity +
                 '\'' +
                '}';
    }
}
