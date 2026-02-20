
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cart {
    private final List<CartItem> items = new ArrayList<>();

    public List<CartItem> getItems() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void addItem(String foodId, String name, double price, int quantity) {
        Optional<CartItem> existing = items.stream()
            .filter(i -> i.getFoodId().equals(foodId))
            .findFirst();
        if (existing.isPresent()) {
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            items.add(new CartItem(foodId, name, price, quantity));
        }
    }

    public void removeItem(String foodId) {
        items.removeIf(i -> i.getFoodId().equals(foodId));
    }

    public void clear() {
        items.clear();
    }

    public double getTotal() {
        return items.stream().mapToDouble(CartItem::getLineTotal).sum();
    }
}
