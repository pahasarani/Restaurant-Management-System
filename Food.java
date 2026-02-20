
import java.util.Objects;

public class Food {
    private String id;
    private String category;
    private String name;
    private double price;
    private int quantity;

    public Food() {}

    public Food(String id, String category, String name, double price, int quantity) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String toRecord() {
        return String.join("|", id, category, name, Double.toString(price), Integer.toString(quantity));
    }

    public static Food fromRecord(String line) {
        if (line == null || line.isBlank()) {
            return null;
        }
        String[] parts = line.split("\\|", -1);
        if (parts.length < 5) {
            return null;
        }
        try {
            double parsedPrice = Double.parseDouble(parts[3]);
            int parsedQty = Integer.parseInt(parts[4]);
            return new Food(parts[0], parts[1], parts[2], parsedPrice, parsedQty);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Food)) {
            return false;
        }
        Food f = (Food) other;
        return Objects.equals(id, f.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
