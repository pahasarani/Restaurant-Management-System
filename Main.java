import java.util.List;
import java.util.Scanner;


public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final UserService USER_SERVICE = new UserService();
    private static final FoodService FOOD_SERVICE = new FoodService();
    private static final PaymentService PAYMENT_SERVICE = new PaymentService();
    private static final OrderService ORDER_SERVICE = new OrderService(FOOD_SERVICE);

    public static void main(String[] args) {
        while (true) {
            printMainMenu();
            int choice = ConsoleUtils.readInt(SCANNER, "Choose option: ", 1, 3);
            switch (choice) {
                case 1:
                    handleLogin();
                    break;
                case 2:
                    handleRegistration();
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    break;
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("===== Welcome to Pahasarani's Kitchen =====");
        System.out.println("1. Login");
        System.out.println("2. Create User Account");
        System.out.println("3. Exit");
        System.out.println("===========================================");
    }

    private static void handleLogin() {
        System.out.println("1. Customer");
        System.out.println("2. Admin");
        int roleChoice = ConsoleUtils.readInt(SCANNER, "Select role: ", 1, 2);
        String role = roleChoice == 1 ? "CUSTOMER" : "ADMIN";
        System.out.print("Username: ");
        String username = SCANNER.nextLine();
        System.out.print("Password: ");
        String password = SCANNER.nextLine();

        User user = USER_SERVICE.login(role, username, password);
        if (user == null) {
            System.out.println("Invalid credentials.");
            return;
        }

        if (role.equals("CUSTOMER")) {
            runCustomerMenu((Customer) user);
        } else {
            runAdminMenu();
        }
    }

    private static void handleRegistration() {
        System.out.print("Choose username: ");
        String username = SCANNER.nextLine();
        System.out.print("Choose password: ");
        String password = SCANNER.nextLine();
        System.out.print("Phone: ");
        String phone = SCANNER.nextLine();
        System.out.print("Address: ");
        String address = SCANNER.nextLine();

        Customer customer = USER_SERVICE.registerCustomer(username, password, phone, address);
        if (customer == null) {
            System.out.println("Registration failed. Username may already exist.");
        } else {
            System.out.println("Account created. You can now log in.");
        }
    }

    private static void runCustomerMenu(Customer customer) {
        Cart cart = new Cart();
        while (true) {
            System.out.println("\n===== Customer Menu =====");
            System.out.println("1. View Foods");
            System.out.println("2. Add Item to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Remove Item from Cart");
            System.out.println("5. Checkout");
            System.out.println("6. Logout");
            int choice = ConsoleUtils.readInt(SCANNER, "Choose option: ", 1, 6);
            switch (choice) {
                case 1:
                    displayFoods();
                    break;
                case 2:
                    addItemToCart(cart);
                    break;
                case 3:
                    showCart(cart);
                    break;
                case 4:
                    removeFromCart(cart);
                    break;
                case 5:
                    checkout(customer, cart);
                    break;
                case 6:
                    return;
                default:
                    break;
            }
        }
    }

    private static void runAdminMenu() {
        while (true) {
            System.out.println("\n===== Admin Menu =====");
            System.out.println("1. View Foods");
            System.out.println("2. Add Food");
            System.out.println("3. Update Food Name");
            System.out.println("4. Update Food Price");
            System.out.println("5. Update Food Quantity");
            System.out.println("6. Delete Food");
            System.out.println("7. View Sales Summary");
            System.out.println("8. Logout");
            int choice = ConsoleUtils.readInt(SCANNER, "Choose option: ", 1, 8);
            switch (choice) {
                case 1:
                    displayFoods();
                    break;
                case 2:
                    addFood();
                    break;
                case 3:
                    updateFoodName();
                    break;
                case 4:
                    updateFoodPrice();
                    break;
                case 5:
                    updateFoodQuantity();
                    break;
                case 6:
                    deleteFood();
                    break;
                case 7:
                    System.out.println(ORDER_SERVICE.getSalesSummary());
                    break;
                case 8:
                    return;
                default:
                    break;
            }
        }
    }

    private static void displayFoods() {
        List<Food> foods = FOOD_SERVICE.getAllFoods();
        if (foods.isEmpty()) {
            System.out.println("No foods available.");
            return;
        }
        System.out.println("\nID | Category | Name | Price | Quantity");
        for (Food f : foods) {
            System.out.println(f.getId() + " | " + f.getCategory() + " | " + f.getName() + " | " + f.getPrice() + " | " + f.getQuantity());
        }
    }

    private static void addItemToCart(Cart cart) {
        List<Food> foods = FOOD_SERVICE.getAllFoods();
        if (foods.isEmpty()) {
            System.out.println("No foods to add.");
            return;
        }
        displayFoods();
        System.out.print("Enter food ID to add: ");
        String id = SCANNER.nextLine();
        Food food = FOOD_SERVICE.findById(id);
        if (food == null) {
            System.out.println("Food not found.");
            return;
        }
        int qty = ConsoleUtils.readInt(SCANNER, "Quantity: ", 1, Math.max(1, food.getQuantity()));
        if (qty > food.getQuantity()) {
            System.out.println("Not enough stock.");
            return;
        }
        cart.addItem(food.getId(), food.getName(), food.getPrice(), qty);
        System.out.println("Added to cart.");
    }

    private static void showCart(Cart cart) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        System.out.println("\nCart items:");
        for (CartItem item : cart.getItems()) {
            System.out.println(item.getFoodId() + " | " + item.getName() + " | qty: " + item.getQuantity() + " | line: " + item.getLineTotal());
        }
        System.out.println("Total: " + cart.getTotal());
    }

    private static void removeFromCart(Cart cart) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        showCart(cart);
        System.out.print("Enter food ID to remove: ");
        String id = SCANNER.nextLine();
        cart.removeItem(id);
        System.out.println("Removed if present.");
    }

    private static void checkout(Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        System.out.println("Select Payment Method:");
        System.out.println("1. Cash on Delivery");
        System.out.println("2. Card Payment");
        int choice = ConsoleUtils.readInt(SCANNER, "Choose option: ", 1, 2);
        PaymentStrategy strategy;
        if (choice == 1) {
            strategy = PAYMENT_SERVICE.cash();
        } else {
            System.out.print("Card Number: ");
            String number = SCANNER.nextLine();
            System.out.print("Expiry (MM/YY): ");
            String expiry = SCANNER.nextLine();
            System.out.print("CVV: ");
            String cvv = SCANNER.nextLine();
            strategy = PAYMENT_SERVICE.card(number, expiry, cvv);
        }
        PaymentResult result = ORDER_SERVICE.placeOrder(customer.getUsername(), cart, strategy);
        System.out.println(result.getMessage());
    }

    private static void addFood() {
        System.out.print("Category: ");
        String category = SCANNER.nextLine();
        System.out.print("Name: ");
        String name = SCANNER.nextLine();
        double price = ConsoleUtils.readDouble(SCANNER, "Price: ", 0.0);
        int qty = ConsoleUtils.readInt(SCANNER, "Quantity: ", 0, Integer.MAX_VALUE);
        Food food = FOOD_SERVICE.addFood(category, name, price, qty);
        System.out.println("Added food with ID: " + food.getId());
    }

    private static void updateFoodName() {
        displayFoods();
        System.out.print("Enter food ID to rename: ");
        String id = SCANNER.nextLine();
        System.out.print("New name: ");
        String newName = SCANNER.nextLine();
        boolean ok = FOOD_SERVICE.updateName(id, newName);
        System.out.println(ok ? "Updated." : "Food not found.");
    }

    private static void updateFoodPrice() {
        displayFoods();
        System.out.print("Enter food ID to reprice: ");
        String id = SCANNER.nextLine();
        double price = ConsoleUtils.readDouble(SCANNER, "New price: ", 0.0);
        boolean ok = FOOD_SERVICE.updatePrice(id, price);
        System.out.println(ok ? "Updated." : "Food not found.");
    }

    private static void updateFoodQuantity() {
        displayFoods();
        System.out.print("Enter food ID to adjust quantity: ");
        String id = SCANNER.nextLine();
        int qty = ConsoleUtils.readInt(SCANNER, "New quantity: ", 0, Integer.MAX_VALUE);
        boolean ok = FOOD_SERVICE.updateQuantity(id, qty);
        System.out.println(ok ? "Updated." : "Food not found.");
    }

    private static void deleteFood() {
        displayFoods();
        System.out.print("Enter food ID to delete: ");
        String id = SCANNER.nextLine();
        boolean ok = FOOD_SERVICE.deleteFood(id);
        System.out.println(ok ? "Deleted." : "Food not found.");
    }
}
