
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MySqroot1 {
    public static void main(String[] args) {
        
        String dynamic="Input.txt";
        String filePath="C:\\Users\\Admin\\Desktop\\"+dynamic;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    double x = Double.parseDouble(line.trim());
                    if (x < 0) {
                        System.out.printf("Please enter a positive number. Invalid input: %.4f%n", x);
                        continue; // skip invalid numbers
                    }

                    double z = 1; // initial guess
                    double tolerance = 0.001; // desired accuracy
                    int maxIterations = 25; 
                    for (int i = 0; i < maxIterations; i++) {
                        double zPrev = z;
                        z -= (z * z - x) / (2 * z);

                        if (Math.abs(z - zPrev) <= tolerance) {
                            break; // stop iterating if desired accuracy is reached
                        }
                    }
                    // System.out.printf("%.4f %.4f%n", x, z);
                    System.out.println("The square root of " + x + " is " + z);

                } catch (NumberFormatException e) {
                    System.out.printf("Invalid input. Please enter a valid number in the file: '%s'%n", line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }
}
    
