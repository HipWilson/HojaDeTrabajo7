package edu.uvg.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class ProductCSVReader {
    public static Optional<Product> findProductBySku(String csvPath, String skuToFind) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line = br.readLine(); // Saltar encabezado
            
            while ((line = br.readLine()) != null) {
                String[] values = splitCSVLine(line);

                if (values.length >= 5) {
                    String currentSku = values[0].trim();
                    if (currentSku.equalsIgnoreCase(skuToFind.trim())) {
                        return Optional.of(new Product(
                            currentSku,
                            parseDoubleOrDefault(values[1], 0.0),
                            parseDoubleOrDefault(values[2], 0.0),
                            values[3].trim(),
                            values[4].trim()
                        ));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
        }
        return Optional.empty();
    }

    private static String[] splitCSVLine(String line) {
        return line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // Divide respetando comillas
    }

    private static double parseDoubleOrDefault(String value, double defaultValue) {
        try {
            return Double.parseDouble(value.replace(',', '.').trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
