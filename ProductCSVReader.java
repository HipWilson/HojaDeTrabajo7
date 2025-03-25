package edu.uvg.util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import edu.uvg.model.Product;

import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class ProductCSVReader {
    public static Optional<Product> findProductBySku(String csvPath, String skuToFind) {
        try (CSVReader reader = new CSVReader(new FileReader(csvPath))) {
            // Lee todas las líneas del CSV
            java.util.List<String[]> allData = reader.readAll();
            
            // Salta la primera línea de encabezados
            for (int i = 1; i < allData.size(); i++) {
                String[] row = allData.get(i);
                
                // Asume el orden de columnas: SKU, Price_Retail, Price_Current, Product_Name, Category
                if (row.length >= 5 && row[0].trim().equals(skuToFind)) {
                    return Optional.of(new Product(
                        row[0].trim(),
                        parseDoubleOrDefault(row[1], 0.0),
                        parseDoubleOrDefault(row[2], 0.0),
                        row[3].trim(),
                        row[4].trim()
                    ));
                }
            }
        } catch (IOException | CsvException e) {
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
        }
        
        return Optional.empty();
    }

    private static double parseDoubleOrDefault(String value, double defaultValue) {
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
