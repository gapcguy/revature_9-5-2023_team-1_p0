package Model;

public class Toy {

    public int toy_id,quantity;
    public String toyName;

    public Toy() {}

    public Toy(int quantity, String toyName) {
        this.quantity = quantity;
        this.toyName = toyName;
    }

    public Toy(int quantity, String toyName,int toy_id) {
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
