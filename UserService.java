
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserService {
    private static final String USERS_FILE = "users.txt";
    private static final String ADMIN_FILE = "admin.txt";

    private final FileManager fileManager = FileManager.getInstance();

    public UserService() {
        ensureDefaultAdmin();
    }

    public Customer registerCustomer(String username, String password, String phone, String address) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return null;
        }
        if (isUsernameTaken(username)) {
            return null;
        }
        Customer customer = new Customer(username, password, phone, address);
        fileManager.appendLine(USERS_FILE, toCustomerRecord(customer));
        return customer;
    }

    public User login(String role, String username, String password) {
        if (role == null || username == null || password == null) {
            return null;
        }
        if (role.equalsIgnoreCase("ADMIN")) {
            return loginAdmin(username, password);
        }
        return loginCustomer(username, password);
    }

    private Admin loginAdmin(String username, String password) {
        List<String> lines = fileManager.readAllLines(ADMIN_FILE);
        for (String line : lines) {
            String[] parts = line.split("\\|", -1);
            if (parts.length >= 2 && parts[0].equals(username) && parts[1].equals(password)) {
                return new Admin(username, password);
            }
        }
        return null;
    }

    private Customer loginCustomer(String username, String password) {
        List<String> lines = fileManager.readAllLines(USERS_FILE);
        for (String line : lines) {
            Customer customer = parseCustomer(line);
            if (customer != null && customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                return customer;
            }
        }
        return null;
    }

    private boolean isUsernameTaken(String username) {
        List<String> adminLines = fileManager.readAllLines(ADMIN_FILE);
        for (String line : adminLines) {
            String[] parts = line.split("\\|", -1);
            if (parts.length >= 1 && parts[0].equalsIgnoreCase(username)) {
                return true;
            }
        }
        List<String> userLines = fileManager.readAllLines(USERS_FILE);
        for (String line : userLines) {
            Customer customer = parseCustomer(line);
            if (customer != null && customer.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    private void ensureDefaultAdmin() {
        List<String> lines = fileManager.readAllLines(ADMIN_FILE);
        if (lines.isEmpty()) {
            fileManager.appendLine(ADMIN_FILE, "admin|admin123");
        }
    }

    private String toCustomerRecord(Customer customer) {
        List<String> parts = new ArrayList<>();
        parts.add(customer.getUsername());
        parts.add(customer.getPassword());
        parts.add(Optional.ofNullable(customer.getPhone()).orElse(""));
        parts.add(Optional.ofNullable(customer.getAddress()).orElse(""));
        return String.join("|", parts);
    }

    private Customer parseCustomer(String line) {
        if (line == null || line.isBlank()) {
            return null;
        }
        String[] parts = line.split("\\|", -1);
        if (parts.length < 4) {
            return null;
        }
        return new Customer(parts[0], parts[1], parts[2], parts[3]);
    }
}
