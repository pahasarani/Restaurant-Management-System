
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class OrderService {
    private static final String ORDERS_FILE = "orders.txt";
    private static final String SALES_FILE = "sales.txt";

    private final FileManager fileManager = FileManager.getInstance();
    private final FoodService foodService;

    public OrderService(FoodService foodService) {
        this.foodService = foodService;
    }

    public PaymentResult placeOrder(String username, Cart cart, PaymentStrategy strategy) {
        if (cart == null || cart.isEmpty()) {
            return new PaymentResult(false, "Cart is empty.");
        }
        if (strategy == null) {
            return new PaymentResult(false, "No payment method selected.");
        }
        // Validate stock before taking payment.
        for (CartItem item : cart.getItems()) {
            Food food = foodService.findById(item.getFoodId());
            if (food == null) {
                return new PaymentResult(false, "Item not found: " + item.getName());
            }
            if (food.getQuantity() < item.getQuantity()) {
                return new PaymentResult(false, "Insufficient stock for " + food.getName());
            }
        }

        double total = cart.getTotal();
        PaymentResult paymentResult = strategy.pay(total);
        if (!paymentResult.isSuccess()) {
            return paymentResult;
        }

        // Deduct stock and persist order records.
        for (CartItem item : cart.getItems()) {
            foodService.reduceStock(item.getFoodId(), item.getQuantity());
        }

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem item : cart.getItems()) {
            orderItems.add(new OrderItem(item.getName(), item.getQuantity(), item.getPrice()));
        }
        Order order = new Order(UUID.randomUUID().toString(), username, orderItems, total, strategy.getName(), LocalDateTime.now());
        fileManager.appendLine(ORDERS_FILE, order.toRecord());
        fileManager.appendLine(SALES_FILE, order.toRecord());
        cart.clear();
        return new PaymentResult(true, "Payment successful. Order confirmed.");
    }

    public String getSalesSummary() {
        List<String> lines = fileManager.readAllLines(SALES_FILE);
        int orderCount = 0;
        double revenue = 0.0;
        int itemsSold = 0;
        for (String line : lines) {
            String[] parts = line.split("\\|", -1);
            if (parts.length < 4) {
                continue;
            }
            orderCount++;
            try {
                revenue += Double.parseDouble(parts[3]);
            } catch (NumberFormatException ignored) {
            }
            String[] orderItems = parts[2].split(",");
            for (String item : orderItems) {
                String[] itemParts = item.split(":", -1);
                if (itemParts.length >= 2) {
                    try {
                        itemsSold += Integer.parseInt(itemParts[1]);
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        }
        return "Orders: " + orderCount + ", Items sold: " + itemsSold + ", Revenue: " + revenue;
    }
}
