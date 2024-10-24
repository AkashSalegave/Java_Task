package Task1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MySqroot1 {
    public static double getSqrootByNewton(double input) {
        double z = 1;
        int maxIteration = 25;
        double tolerance = 0.001;

        for (int i = 0; i < maxIteration; i++) {
            double previousZ = z;
            z -= (z * z - input) / (2 * z); // Newton's method formula.

            if (Math.abs(z - previousZ) <= tolerance) { // Check for tolerance.
                break;
            }
        }
        return z;
    }

    public static void main(String[] args) {

        // Specify the path
        String filePath = "C:\\Users\\Admin\\Desktop\\Java_Task\\Task1\\Input.txt"; // File path declare.

        // BufferedReader-Reads text from a character-input stream
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }

                try {
                    double input = Double.parseDouble(line);

                    if (input < 0) { // Check for negative input.
                        System.out.printf("%.2f Number provided is negative.%n", input);
                    } else if (input == 0) { // Check for zero.
                        System.out.printf("%.2f Square root of zero is 0.%n", input);
                    } else {
                        double result = getSqrootByNewton(input); // Calculate the square root.
                        System.out.printf("%.2f %.4f %n", input, result);
                    }
                } catch (NumberFormatException e) { // Handle parsing errors
                    System.out.printf("%s Invalid input. Please enter a valid number %n", line);
                }
            }
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}
