
public class CartItem {
    private final String foodId;
    private final String name;
    private final double price;
    private int quantity;

    public CartItem(String foodId, String name, double price, int quantity) {
        this.foodId = foodId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getFoodId() {
        return foodId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getLineTotal() {
        return price * quantity;
    }
}
