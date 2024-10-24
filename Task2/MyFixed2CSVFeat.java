package Task2;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MyFixed2CSVFeat 
{
    public static void main(String[] args) 
    {
        if (args.length < 4) // Updated to require at least 4 arguments
        {
            System.out.println("Usage: myfixed2csv <file-name> <n-columns> <length1> <length2> ... <output-file-name>");
            return;
        }
        
        String fileName = args[0];      
        String inputFilePath = "C:\\Users\\Admin\\Desktop\\" + fileName; // Input file path
        
        // Read the output file name from arguments
        String outputFileName = args[args.length - 1]; // Last argument is the output file name
        String outputFilePath = "C:\\Users\\Admin\\Desktop\\Java_Task\\Task2\\output\\" + outputFileName; // Output CSV path
        
        // Check if the output file already exists
        if (Files.exists(Paths.get(outputFilePath))) {
            System.err.println("Error: Output file \"" + outputFileName + "\" already exists.");
            return; // Exit if the file exists
        }

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
            System.out.println("CSV file generated successfully: " + outputFileName);
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
            if (currentIndex + length <= line.length())  
            {
                String field = line.substring(currentIndex, currentIndex + length).trim(); // Trim spaces
                field = field.replaceFirst("^0+", ""); // Remove leading zeros
                csvLine.append("\"").append(field).append("\""); // Enclose in double quotes

                currentIndex += length;
                if (currentIndex < line.length()) 
                {
                    csvLine.append(","); // Add comma separator if not at the end of the line
                }
            }
        }

        return csvLine.toString();
    }
}