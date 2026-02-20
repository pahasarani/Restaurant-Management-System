

/**
 * Factory for creating users by role keyword.
 */
public class UserFactory {
    public static User createUser(String role) {
        if (role == null) {
            return null;
        }
        String normalized = role.trim().toUpperCase();
        if ("ADMIN".equals(normalized)) {
            return new Admin();
        }
        if ("CUSTOMER".equals(normalized)) {
            return new Customer();
        }
        return null;
    }
}
