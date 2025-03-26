package edu.uvg.util;

import java.io.*;

public class ProductCSVReader {

    public static BinarySearchTree<Product> loadProductsIntoBST() {
        String csvPath = "productos.csv"; // Ruta relativa en la raíz del proyecto
        File file = new File(csvPath);

        if (!file.exists()) {
            System.err.println("Error: No se encontró el archivo CSV en: " + file.getAbsolutePath());
            return new BinarySearchTree<>();
        }

        BinarySearchTree<Product> bst = new BinarySearchTree<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // Saltar encabezado
            while ((line = br.readLine()) != null) {
                String[] values = splitCSVLine(line);
                if (values.length >= 5) {
                    Product product = new Product(
                        values[0].trim(),
                        parseDoubleOrDefault(values[1], 0.0),
                        parseDoubleOrDefault(values[2], 0.0),
                        values[3].trim(),
                        values[4].trim()
                    );
                    bst.insert(product);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
        }
        return bst;
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
