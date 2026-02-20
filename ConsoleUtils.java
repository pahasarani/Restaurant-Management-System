
import java.util.Scanner;

public class ConsoleUtils {
    public static int readInt(Scanner scanner, String prompt, int min, int max) {
        int value;
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                value = Integer.parseInt(line.trim());
                if (value < min || value > max) {
                    System.out.println("Enter a number between " + min + " and " + max + ".");
                    continue;
                }
                return value;
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    public static double readDouble(Scanner scanner, String prompt, double min) {
        double value;
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                value = Double.parseDouble(line.trim());
                if (value < min) {
                    System.out.println("Value must be at least " + min + ".");
                    continue;
                }
                return value;
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }
}
