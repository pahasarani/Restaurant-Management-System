
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class Order {
    private final String id;
    private final String username;
    private final List<OrderItem> items;
    private final double total;
    private final String paymentMethod;
    private final LocalDateTime placedAt;

    public Order(String id, String username, List<OrderItem> items, double total, String paymentMethod, LocalDateTime placedAt) {
        this.id = id;
        this.username = username;
        this.items = items;
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.placedAt = placedAt;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public LocalDateTime getPlacedAt() {
        return placedAt;
    }

    public String toRecord() {
        String itemBlock = items.stream()
            .map(OrderItem::toRecord)
            .collect(Collectors.joining(","));
        return String.join("|",
            id,
            username,
            itemBlock,
            Double.toString(total),
            paymentMethod,
            placedAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
    }
}
