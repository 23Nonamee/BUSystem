package com.busystem.ui.cli;

import com.busystem.application.ProductService;
import com.busystem.domain.Product;


import java.math.BigDecimal;
import java.util.Optional;
import java.util.Scanner;

/**
 * ProductConsoleUI
 */
public class ProductConsoleUI {
  private final ProductService productService;
  private final Scanner scanner;

  public ProductConsoleUI (ProductService productService, Scanner scanner ) {
      this.productService = productService;
      this.scanner = scanner;

  }
  
  public void start(){
      int option = -1;
      while (!(option == 0)) {
      printMenu();
      option = Integer.parseInt(scanner.nextLine().trim());

      switch (option) {
          case 1 -> handleRegisterProduct();
          case 2 -> handleFindProductById();    
          case 3 -> handleListAllProducts();
          case 4 -> handleAdjustProductStock();
          case 0 -> System.out.println("Return to main menu...");
          default -> System.out.println("Invalid Option, try again");
      }
    }
  }
  
  private void printMenu() {

    System.out.println("         📦PRODUCTS MENU           ");
    System.out.println("=================================");
    System.out.println("1) Register a product");
    System.out.println("2) Find a product by ID");
    System.out.println("3) List all products");
    System.out.println("4) Adjust product stock");
    System.out.println("0) Return main menu");
    System.out.println("=================================");
  } 

  private void handleRegisterProduct(){

    System.out.println("\n--- 📝 Register a product ---");

    String id = readStringInput("Enter the Product ID (ex. P-120): ");
    String name = readStringInput("Enter the Product name: " );
    BigDecimal price = readBigDecimalInput("Enter the Product price (ex. 25.5): ");
    int stock = readIntInput("Enter the stock: ");
    
    try {
    Product product = new Product(name, id, price, stock);
    productService.registerProduct(product);
    System.out.println("✅ Product registered successfully!");
    } catch (IllegalArgumentException e) {
      System.out.println("❌ Validation Error: " + e.getMessage());
    }
  }
  

  private void handleFindProductById(){
    System.out.println("\n---🔍Find a product---");
    String id = readStringInput("Enter the Product ID: ");
    
    Optional<Product> optionalProductSearch = productService.getProductById(id);


    if (optionalProductSearch.isPresent()){

      Product p = optionalProductSearch.get();
      System.out.println("\n----------------------------------------------");
      System.out.printf("ID: %s | Name: %s | Price: %.2f | Stock: %d\n",
              p.getId(), p.getName(), p.getPrice(), p.getStock());
      System.out.println("----------------------------------------------");
    } else {
      System.out.println("⚠️No products find with the ID: " + id );
    }
  }

  private void handleListAllProducts(){
    System.out.println(productService.getAllProducts());
  }



  private void handleAdjustProductStock(){
    String id = readStringInput("Enter the product ID: ");
    int quantity = readIntInput("Enter the product stock: ");
    productService.adjustProductStock(id, quantity);

    System.out.printf("\nThe product stock was set to: %d\n ", quantity);
  }




  // INPUT METHODS 

  private String readStringInput(String prompt){
    System.out.print(prompt);
    return scanner.nextLine().trim();

  }

  private int readIntInput(String prompt){
    while (true) {
      try {
        System.out.print(prompt);

          return Integer.parseInt(scanner.nextLine().trim());

      } catch (NumberFormatException e) {

        System.out.println("⚠️Invalid Input. Enter a Integer "); 
      }
    }
  }
  
  private BigDecimal readBigDecimalInput(String prompt){
    while (true) {
      try {
        System.out.print(prompt);

        return new BigDecimal(scanner.nextLine().trim());

      } catch (NumberFormatException e) {

        System.out.println("⚠️Invalid Input. Enter a valid price (ex. 25.5)");
      }
    }
  }

}
