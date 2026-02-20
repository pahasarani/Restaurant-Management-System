
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class FoodService {
    private static final String FOODS_FILE = "foods.txt";

    private final FileManager fileManager = FileManager.getInstance();

    public List<Food> getAllFoods() {
        List<String> lines = fileManager.readAllLines(FOODS_FILE);
        List<Food> foods = new ArrayList<>();
        for (String line : lines) {
            Food f = Food.fromRecord(line);
            if (f != null) {
                foods.add(f);
            }
        }
        foods.sort(Comparator.comparing(Food::getCategory).thenComparing(Food::getName));
        return foods;
    }

    public Food addFood(String category, String name, double price, int quantity) {
        String id = UUID.randomUUID().toString();
        Food food = new Food(id, category, name, price, quantity);
        fileManager.appendLine(FOODS_FILE, food.toRecord());
        return food;
    }

    public boolean updateName(String foodId, String newName) {
        List<Food> foods = getAllFoods();
        boolean updated = false;
        for (Food f : foods) {
            if (f.getId().equals(foodId)) {
                f.setName(newName);
                updated = true;
                break;
            }
        }
        persist(foods, updated);
        return updated;
    }

    public boolean updatePrice(String foodId, double newPrice) {
        List<Food> foods = getAllFoods();
        boolean updated = false;
        for (Food f : foods) {
            if (f.getId().equals(foodId)) {
                f.setPrice(newPrice);
                updated = true;
                break;
            }
        }
        persist(foods, updated);
        return updated;
    }

    public boolean updateQuantity(String foodId, int quantity) {
        List<Food> foods = getAllFoods();
        boolean updated = false;
        for (Food f : foods) {
            if (f.getId().equals(foodId)) {
                f.setQuantity(quantity);
                updated = true;
                break;
            }
        }
        persist(foods, updated);
        return updated;
    }

    public boolean deleteFood(String foodId) {
        List<Food> foods = getAllFoods();
        boolean removed = foods.removeIf(f -> f.getId().equals(foodId));
        persist(foods, removed);
        return removed;
    }

    public Food findById(String id) {
        return getAllFoods().stream()
            .filter(f -> f.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    public List<Food> findByCategory(String category) {
        return getAllFoods().stream()
            .filter(f -> f.getCategory().equalsIgnoreCase(category))
            .collect(Collectors.toList());
    }

    public boolean reduceStock(String id, int amount) {
        List<Food> foods = getAllFoods();
        boolean updated = false;
        for (Food f : foods) {
            if (f.getId().equals(id) && f.getQuantity() >= amount) {
                f.setQuantity(f.getQuantity() - amount);
                updated = true;
                break;
            }
        }
        persist(foods, updated);
        return updated;
    }

    public List<String> listCategories() {
        return getAllFoods().stream()
            .map(Food::getCategory)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }

    private void persist(List<Food> foods, boolean shouldWrite) {
        if (shouldWrite) {
            List<String> lines = foods.stream()
                .map(Food::toRecord)
                .collect(Collectors.toList());
            fileManager.overwrite(FOODS_FILE, lines);
        }
    }
}
