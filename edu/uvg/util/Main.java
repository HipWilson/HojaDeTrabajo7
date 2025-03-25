package edu.uvg.util;

import edu.uvg.util.ProductCSVReader;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Ruta del archivo CSV (ajusta según tu repositorio)
        String csvPath = "C:\\Users\\wilso\\Documents\\GitHub\\HojaDeTrabajo7\\edu\\uvg\\util\\productos.csv";
        
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n--- Búsqueda de Productos ---");
            System.out.println("Ingrese el SKU del producto (o 'salir' para terminar):");
            
            String skuInput = scanner.nextLine().trim();
            
            if (skuInput.equalsIgnoreCase("salir")) {
                break;
            }
            
            Optional<Product> productFound = ProductCSVReader.findProductBySku(csvPath, skuInput);
            
            if (productFound.isPresent()) {
                System.out.println("\nProducto encontrado:");
                System.out.println(productFound.get());
            } else {
                System.out.println("No se encontró ningún producto con el SKU: " + skuInput);
                
                // Depuración adicional
                System.out.println("Detalles de depuración:");
                debugCSVFile(csvPath, skuInput);
            }
        }
        
        scanner.close();
        System.out.println("Programa finalizado.");
    }
    
    // Método de depuración para revisar el contenido del CSV
    private static void debugCSVFile(String csvPath, String skuToFind) {
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(csvPath))) {
            String line;
            int lineNumber = 0;
            
            while ((line = br.readLine()) != null) {
                lineNumber++;
                
                // Imprimir información de cada línea
                System.out.println("Línea " + lineNumber + ": " + line);
                
                // Si quieres buscar coincidencias parciales
                if (line.contains(skuToFind)) {
                    System.out.println("COINCIDENCIA PARCIAL en línea " + lineNumber);
                }
            }
        } catch (java.io.IOException e) {
            System.err.println("Error al leer el archivo para depuración: " + e.getMessage());
        }
    }
}