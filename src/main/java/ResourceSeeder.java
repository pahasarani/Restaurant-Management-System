import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Copies default data files from classpath resources to the working directory
 * if they do not exist or are empty. This keeps the app runnable when packaged
 * with Maven/NetBeans while still using simple relative file access.
 */
public class ResourceSeeder {
    private static final String RESOURCE_BASE = "/data/";
    private static final String[] FILES = {
        "users.txt",
        "admin.txt",
        "foods.txt",
        "orders.txt",
        "sales.txt"
    };

    public static void seedDefaults() {
        for (String name : FILES) {
            Path target = Paths.get(name);
            if (isMissingOrEmpty(target)) {
                copyFromResource(name, target);
            }
        }
    }

    private static boolean isMissingOrEmpty(Path path) {
        try {
            return Files.notExists(path) || Files.size(path) == 0;
        } catch (IOException ex) {
            return true;
        }
    }

    private static void copyFromResource(String name, Path target) {
        try (InputStream in = ResourceSeeder.class.getResourceAsStream(RESOURCE_BASE + name)) {
            if (in == null) {
                System.err.println("Default resource not found for " + name);
                return;
            }
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Seeded default " + name + " from resources.");
        } catch (IOException ex) {
            System.err.println("Failed to seed " + name + ": " + ex.getMessage());
        }
    }
}
