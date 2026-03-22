# Restaurant Management System (Console)

Console-based restaurant app with admin and customer roles, menu CRUD, add-on services, cart/checkout, and text-file persistence.

## Design patterns
- Strategy: Payment methods implement [PaymentStrategy.java](PaymentStrategy.java) with [CardPaymentStrategy.java](CardPaymentStrategy.java), [CashPaymentStrategy.java](CashPaymentStrategy.java), and [WalletPaymentStrategy.java](WalletPaymentStrategy.java) chosen at runtime via [PaymentProcessor.java](PaymentProcessor.java).
- Repository-like utility: [DataStore.java](DataStore.java) centralizes loading/saving menu items, services, and users, keeping I/O separate from business logic.
- Service layer: [CartService.java](CartService.java) and [OrderService.java](OrderService.java) encapsulate cart math and totals; [Main.java](Main.java) orchestrates UI flows.

## CRUD coverage
- Menu items: admin can create/read/update/delete dishes (id, name, size/portion, category, price, quantity) in the catalog menu; persisted via [DataStore.java](DataStore.java).
- Add-on services: admin can create/read/update/delete optional services (e.g., delivery tiers, packaging) stored in [services.txt](services.txt).
- Users: customers can register (create) and login (read); credentials persist in [users.txt](users.txt).

## Roles
- Admin (default credentials in [Main.java](Main.java): user `bypahasarani`, pass `np2026`): manage menu CRUD, manage services CRUD, view catalog.
- Customer: view menu, add/update/remove cart items, select optional services, choose payment method, checkout.

### Admin functions
- Add, update, delete menu items (catalog CRUD).
- Add, update, delete optional services (e.g., delivery/packaging tiers).
- View full catalog and services.

### Customer functions / how to order
1. Login or create an account.
2. View menu and add items to cart (quantities validated against stock).
3. View/update/remove items in cart.
4. Choose optional services (e.g., delivery, gift wrap).
5. Pick payment method (card, cash, wallet) and checkout; payment uses the selected strategy.
6. On success, stock is reduced and cart is cleared.

## Data files
- Menu items: [catalog.txt](catalog.txt)
- Optional services: [services.txt](services.txt)
- Users: [users.txt](users.txt)
- Seed defaults are auto-created on first run by [DataStore.java](DataStore.java).

## How to run (CLI)
- From the project folder: `javac *.java` then `java Main`.
- Keep the text files in the same folder so reads/writes work.

## How to run (NetBeans)
- File → New Project → Java with Ant → Java Project with Existing Sources.
- Set Project Folder to the Online Clothing folder; set Source Package Folders to the same folder (default package).
- After import, set the Main Class to `Main` (Project Properties → Run → Browse → Main).
- Run (F6). Ensure the text files remain at the project root for persistence.
