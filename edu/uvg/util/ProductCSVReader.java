package edu.uvg.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class ProductCSVReader {
    public static Optional<Product> findProductBySku(String csvPath, String skuToFind) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            // Saltar la primera línea de encabezados
            String line = br.readLine();
            
            while ((line = br.readLine()) != null) {
                // Dividir la línea por comas, manejando casos con comas dentro de campos
                String[] values = splitCSVLine(line);
                
                // Verificar que tengamos suficientes columnas
                if (values.length >= 5) {
                    // Trim y comparación sin considerar mayúsculas/minúsculas
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
            e.printStackTrace();
        }
        
        return Optional.empty();
    }

    // Método para manejar líneas CSV complejas
    private static String[] splitCSVLine(String line) {
        // Separador por comas, pero respetando campos entre comillas
        java.util.List<String> result = new java.util.ArrayList<>();
        boolean inQuotes = false;
        StringBuilder current = new StringBuilder();
        
        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString().trim());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        
        // Agregar el último campo
        result.add(current.toString().trim());
        
        return result.toArray(new String[0]);
    }

    private static double parseDoubleOrDefault(String value, double defaultValue) {
        try {
            // Reemplazar coma por punto decimal si es necesario
            value = value.replace(',', '.');
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}