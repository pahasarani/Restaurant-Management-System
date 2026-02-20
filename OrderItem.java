
public class OrderItem {
    private final String name;
    private final int quantity;
    private final double pricePerUnit;

    public OrderItem(String name, int quantity, double pricePerUnit) {
        this.name = name;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public double getLineTotal() {
        return pricePerUnit * quantity;
    }

    public String toRecord() {
        return name + ":" + quantity + ":" + pricePerUnit;
    }

    public static OrderItem fromRecord(String record) {
        if (record == null || record.isBlank()) {
            return null;
        }
        String[] parts = record.split(":", -1);
        if (parts.length < 3) {
            return null;
        }
        try {
            int qty = Integer.parseInt(parts[1]);
            double price = Double.parseDouble(parts[2]);
            return new OrderItem(parts[0], qty, price);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
