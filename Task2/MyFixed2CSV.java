package Task2;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;



public class MyFixed2CSV 
{
    public static void main(String[] args) 
    {
        if (args.length < 3) 
        {
            System.out.println("Usage: myfixed2csv <file-name> <n-columns> <length1> <length2> ...");
            return;
        }
        
        String fileName = args[0];      
        String inputFilePath = "C:\\Users\\Admin\\Desktop\\" + fileName; // Input file path
        String outputFilePath = "C:\\Users\\Admin\\Desktop\\Java_Task\\Task2\\output\\output1.csv"; // Output CSV path
        
        int nColumns = Integer.parseInt(args[1]);
        int[] columnLengths = Arrays.stream(args, 2, nColumns + 2) // Read column lengths correctly
                                    .mapToInt(Integer::parseInt)
                                    .toArray();
  
        try {
            // Read lines, process each line to convert to CSV format
            List<String> csvLines = Files.lines(Paths.get(inputFilePath))
                    .map(line -> convertLineToCSV(line, columnLengths))
                    .collect(Collectors.toList());

            // Write the output CSV to a file
            Files.write(Paths.get(outputFilePath), csvLines, StandardOpenOption.CREATE);
            System.out.println("CSV file generated successfully: " + "output.csv");
        }
        
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String convertLineToCSV(String line, int[] columnLengths) 
    {
        StringBuilder csvLine = new StringBuilder();
        int currentIndex = 0;
        
        System.out.println(line);

        for (int length : columnLengths) 
        {
        
        	// Ensure the index is within bounds to prevent StringIndexOutOfBoundsException
            if (currentIndex + length <= line.length())  // 10 4 4 15   // 33 
            {
                String field = line.substring(currentIndex, currentIndex + length).trim(); // Trim spaces
                field = field.replaceFirst("^0+", ""); // Remove leading zeros
                csvLine.append("\"").append(field).append("\""); // Enclose in double quotes

                currentIndex += length;//10 + 4 =14 +4 =18+15=33
                if (currentIndex < line.length()) 
                {
                    csvLine.append(","); // Add comma separator if not at the end of the line
                }
            }
        }

        return csvLine.toString();
    }
}