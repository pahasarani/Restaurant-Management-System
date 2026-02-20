
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Singleton responsible for basic text file I/O and bootstrapping data files.
 */
public class FileManager {
    private static final String[] REQUIRED_FILES = new String[] {
        "users.txt",
        "admin.txt",
        "foods.txt",
        "orders.txt",
        "sales.txt"
    };

    private static FileManager instance;

    private FileManager() {
        ensureFilesExist();
    }

    public static synchronized FileManager getInstance() {
        if (instance == null) {
            instance = new FileManager();
        }
        return instance;
    }

    public List<String> readAllLines(String fileName) {
        Path path = Paths.get(fileName);
        try {
            if (Files.notExists(path)) {
                return Collections.emptyList();
            }
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.err.println("Failed to read " + fileName + ": " + ex.getMessage());
            return Collections.emptyList();
        }
    }

    public void overwrite(String fileName, List<String> lines) {
        Path path = Paths.get(fileName);
        try {
            Files.write(path, new ArrayList<>(lines), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException ex) {
            System.err.println("Failed to write " + fileName + ": " + ex.getMessage());
        }
    }

    public void appendLine(String fileName, String line) {
        Path path = Paths.get(fileName);
        try {
            Files.write(path, List.of(line), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException ex) {
            System.err.println("Failed to append to " + fileName + ": " + ex.getMessage());
        }
    }

    private void ensureFilesExist() {
        for (String name : REQUIRED_FILES) {
            Path path = Paths.get(name);
            try {
                if (Files.notExists(path)) {
                    Files.createFile(path);
                }
            } catch (IOException ex) {
                System.err.println("Could not create file " + name + ": " + ex.getMessage());
            }
        }
    }
}
