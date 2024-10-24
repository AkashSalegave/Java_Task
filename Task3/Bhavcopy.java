package Task3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Scanner;

public class Bhavcopy {

    private List<String[]> data = new ArrayList<>();
    private Map<String, String[]> symbolMap = new HashMap<>();
    public Bhavcopy(String filename) throws IOException {
        loadData(filename);
    }

    private void loadData(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false; // Skip header
                    continue;
                }
                String[] values = line.split(",");
                data.add(values);
                if (values.length > 0) {
                    symbolMap.put(values[0], values); // Map symbol to its data
                }
            }
        }
    }

    // 1.Fetch Symbol
    public String getSymbolInfo(String symbol) {
        String[] info = symbolMap.get(symbol);
        return info != null ? String.join(",", info) : "Symbol not found";
    }

    // 2.Count Symbol by Series
    public int countSymbolsBySeries(String series) {
        int count = 0;
        for (String[] row : data) {
            if (row.length > 1 && row[1].trim().equalsIgnoreCase(series.trim())) {
                count++;
            }
        }
        return count;
    }

    public List<String> gainSymbols(double threshold) {
        List<String> result = new ArrayList<>();
        for (String[] row : data) {
            if (row.length > 5) {
                double open = Double.parseDouble(row[4]);
                double close = Double.parseDouble(row[8]);
                double gainPercentage = ((close - open) / open) * 100;
                if (gainPercentage > threshold) {
                    result.add(row[0]);
                }
            }
        }
        return result;
    }

    public List<String> topBotSymbols(double threshold) {
        List<String> result = new ArrayList<>();
        for (String[] row : data) {
            if (row.length > 4) {
                double high = Double.parseDouble(row[5]);
                double low = Double.parseDouble(row[6]);
                double topBotPercentage = ((high - low) / low) * 100;
                if (topBotPercentage > threshold) {
                    result.add(row[0]);
                }
            }
        }
        return result;
    }

    public double standardDeviation(String series) {
        List<Double> closes = new ArrayList<>();
        for (String[] row : data) {
            if (row.length > 1 && row[1].trim().equalsIgnoreCase(series.trim())) {
                closes.add(Double.parseDouble(row[8]));
            }
        }
        return calculateStandardDeviation(closes);
    }

    private double calculateStandardDeviation(List<Double> values) {
        if (values.size() == 0)
            return 0.0;
        double mean = values.stream().mapToDouble(val -> val).average().orElse(0.0);
        double variance = values.stream().mapToDouble(val -> Math.pow(val - mean, 2)).average().orElse(0.0);
        return Math.sqrt(variance);
    }

    // 6. Top N symbols having maximum gain
    public List<String> topNGainers(int n) {
        return data.stream()
                .map(row -> new AbstractMap.SimpleEntry<>(row[0],
                        (Double.parseDouble(row[8]) - Double.parseDouble(row[4])) / Double.parseDouble(row[4]))) // Calculate
                                                                                                                 // gain
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()).limit(n).map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    // 7. Bottom N symbols having lowest gain
    public List<String> bottomNLossers(int n) {
        return data.stream()
                .map(row -> new AbstractMap.SimpleEntry<>(row[0],
                        (Double.parseDouble(row[8]) - Double.parseDouble(row[4])) / Double.parseDouble(row[4]))) // Calculate
                                                                                                                 // gain
                .sorted(Map.Entry.comparingByValue()).limit(n).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    // 8. Top N most traded (by volume) symbols
    public List<String> topNTraded(int n) {
        return data.stream()
                .sorted((row1, row2) -> Double.compare(Double.parseDouble(row2[10]), Double.parseDouble(row1[10]))) // TTL_TRD_QNTY
                .limit(n)
                .map(row -> row[10])
                .collect(Collectors.toList());
    }

    // 9. Bottom N least traded (by volume) symbols
    public List<String> bottomNTraded(int n) {
        return data.stream().sorted(Comparator.comparingDouble(row -> Double.parseDouble(row[10]))) // TTL_TRD_QNTY
                .limit(n).map(row -> row[0]).collect(Collectors.toList());
    }

    // 10. Highest and lowest traded (by TOTRDVAL) for a given series
    public List<String> highLowBySeries(String series) {
        List<Map.Entry<String, Double>> tradedValues = data.stream()
                .filter(row -> row[1].trim().equalsIgnoreCase(series.trim()))
                .map(row -> new AbstractMap.SimpleEntry<>(row[0], // SYMBOL
                        Double.parseDouble(row[10]) * Double.parseDouble(row[6]) // TTL_TRD_QNTY * LAST_PRICE
                )).collect(Collectors.toList());

        if (tradedValues.isEmpty()) {
            return Arrays.asList("No data available");
        }

        String highest = tradedValues.stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey)
                .orElse("No highest");

        String lowest = tradedValues.stream().min(Map.Entry.comparingByValue()).map(Map.Entry::getKey)
                .orElse("No lowest");

        return Arrays.asList(highest, lowest);
    }

    public static void main(String[] args) {
        try {
            Bhavcopy bhavcopy = new Bhavcopy("C:\\Users\\Admin\\Desktop\\Java_Task\\Task3\\Bhavcopy1.csv");
            Scanner scanner = new Scanner(System.in);
            System.out.println(" Please enter input ");
            String command;

            while (true) {

                command = scanner.nextLine().trim();
                if (command.equalsIgnoreCase("quit")) {
                    break;
                }

                String[] parts = command.split(" ");

                switch (parts[0].toUpperCase()) {

                    case "SYMBOL":
                        if (parts.length == 2) {
                            System.out.println(bhavcopy.getSymbolInfo(parts[1]));
                        }
                        break;
                    case "COUNT":
                        if (parts.length == 2) {
                            System.out.println(bhavcopy.countSymbolsBySeries(parts[1]));
                        }
                        break;
                    case "GAIN":
                        if (parts.length == 2) {
                            double gainThreshold = Double.parseDouble(parts[1]);
                            System.out.println(String.join(" ", bhavcopy.gainSymbols(gainThreshold)));
                        }
                        break;
                    case "TOPBOT":
                        if (parts.length == 2) {
                            double topBotThreshold = Double.parseDouble(parts[1]);
                            System.out.println(String.join(" ", bhavcopy.topBotSymbols(topBotThreshold)));
                        }
                        break;

                    case "STDDEV":
                        if (parts.length == 2) {
                            double stdDev = bhavcopy.standardDeviation(parts[1]);
                            System.out.printf("%.2f%n", stdDev);
                        }
                        break;

                    case "TOPGAINER":
                        int topN = Integer.parseInt(parts[1]);
                        System.out.println("Top " + topN + " gainers: " + bhavcopy.topNGainers(topN));
                        break;

                    case "TOPLAGGARDS":
                        int bottomN = Integer.parseInt(parts[1].trim());
                        System.out.println("Bottom " + bottomN + " laggards: " + bhavcopy.bottomNLossers(bottomN));
                        break;

                    case "TOPTRADED":
                        int mostTradedN = Integer.parseInt(parts[1].trim());
                        System.out.println("Top " + mostTradedN + " most traded: " + bhavcopy.topNTraded(mostTradedN));
                        break;

                    case "BOTTRADED":
                        int leastTradedN = Integer.parseInt(parts[1].trim());
                        System.out.println(
                                "Bottom " + leastTradedN + " least traded: " + bhavcopy.bottomNTraded(leastTradedN));
                        break;

                    case "HIGHLOW":
                        System.out.println("Highest and lowest traded in " + parts[1] + " series: "
                                + bhavcopy.highLowBySeries(parts[1]));
                        break;

                    default:
                        System.out.println("Unknown command");
                        break;
                }
            }

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}