package edu.uvg.util;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Cargar productos en el BST desde un archivo en la raíz del proyecto
        BinarySearchTree<Product> productTree = ProductCSVReader.loadProductsIntoBST();
        
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n--- Búsqueda de Productos ---");
            System.out.println("Ingrese el SKU del producto (o 'salir' para terminar):");
            
            String skuInput = scanner.nextLine().trim();
            
            if (skuInput.equalsIgnoreCase("salir")) {
                break;
            }
            
            Product searchProduct = new Product(skuInput, 0, 0, "", ""); // Solo usamos el SKU para buscar
            Product productFound = productTree.search(searchProduct);
            
            if (productFound != null) {
                System.out.println("\nProducto encontrado:");
                System.out.println(productFound);
            } else {
                System.out.println("No se encontró ningún producto con el SKU: " + skuInput);
            }
        }
        
        scanner.close();
        System.out.println("Programa finalizado.");
    }
}
