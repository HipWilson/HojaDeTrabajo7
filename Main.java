package edu.uvg.app;

import edu.uvg.model.Product;
import edu.uvg.util.ProductCSVReader;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Ruta del archivo CSV (ajusta según tu repositorio)
        String csvPath = "productos.csv";
        
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
            }
        }
        
        scanner.close();
        System.out.println("Programa finalizado.");
    }
}