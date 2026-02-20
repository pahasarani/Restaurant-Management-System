# Pahasarani's Kitchen (Console)

Java console app for restaurant management with admin/customer roles, CRUD for menu items, text-file persistence, and cash/card payments.

## Quick start
- Requirements: Java 17+ (or 11+ should also work).
- From the Online Food folder:
  - Compile: `javac -d out *.java`
  - Run: `java -cp out Main`
- Data files (`users.txt`, `admin.txt`, `foods.txt`, `orders.txt`, `sales.txt`) are auto-created on first run.

## Admin login
- Default admin username: `admin`
- Default admin password: `admin123`
- Stored in `admin.txt` (seeded automatically if missing).

## Menus
- Main menu: Login, Create User Account, Exit.
- Customer menu: View Foods, Add to Cart, View Cart, Remove from Cart, Checkout, Logout.
- Admin menu: View Foods, Add Food, Update Food Name/Price/Quantity, Delete Food, View Sales Summary, Logout.

## How to use
1) Run the app and choose **Create User Account** to register a customer.
2) Login as **Admin** (admin/admin123) to seed foods: add categories/items with price and quantity.
3) Login as **Customer** to browse, add to cart, and checkout using cash or card.
4) Admin can later update or delete items and view sales summary.

## CRUD coverage (foods)
- Create: Admin adds a food item (category, name, price, quantity).
- Read: Admin/Customer view list of foods (sorted by category/name).
- Update: Admin can change name, price, or quantity.
- Delete: Admin can remove a food item.

## Files and persistence
- Menu items: `foods.txt`
- Users: `users.txt`
- Admin credentials: `admin.txt`
- Orders history: `orders.txt`
- Sales aggregation: `sales.txt`
- File I/O helper ensures files exist and handles read/append/overwrite.

## Design patterns
- **Singleton**: `FileManager` provides a single shared instance for file I/O and bootstrap of data files.
- **Strategy**: `PaymentStrategy` with `CashPaymentStrategy` and `CardPaymentStrategy`; selected at checkout without changing order flow.

## SOLID mapping
- **Single Responsibility**: `UserService`, `FoodService`, `OrderService`, `PaymentService`, `FileManager` each own one responsibility.
- **Open/Closed**: New payment methods can be added by implementing `PaymentStrategy` without modifying checkout flow.
- **Liskov Substitution**: `PaymentStrategy` implementations, `OrderService` using any strategy instance; user types via factory if extended later.
- **Interface Segregation**: Payment behaviors isolated in `PaymentStrategy`; console helpers in `ConsoleUtils` to avoid bloating services.
- **Dependency Inversion**: High-level logic depends on abstractions (`PaymentStrategy`) and services, not on concrete payment types.

## Key classes
- Entry point and menus: [Main.java](Main.java)
- File I/O singleton: [FileManager.java](FileManager.java)
- Domain: [Food.java](Food.java), [Cart.java](Cart.java), [Order.java](Order.java) and supporting items.
- Services: [UserService.java](UserService.java), [FoodService.java](FoodService.java), [OrderService.java](OrderService.java), [PaymentService.java](PaymentService.java)
- Payment strategies: [PaymentStrategy.java](PaymentStrategy.java), [CashPaymentStrategy.java](CashPaymentStrategy.java), [CardPaymentStrategy.java](CardPaymentStrategy.java)

## Notes
- Card validation is basic (length/format checks only) and for demo purposes.
- Sales summary is aggregated from `sales.txt` and shows order count, items sold, and revenue.
