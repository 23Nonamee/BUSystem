package com.busystem;

import com.busystem.ui.cli.ProductConsoleUI;
import com.busystem.ui.cli.MainConsoleUI;
import com.busystem.application.ProductService;
import com.busystem.domain.ProductRepository;
import com.busystem.infrastructure.InMemoryProductRepository;

import java.util.Scanner;


/**
 * MainApp
 */
public class MainApp {
  
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);  

    ProductRepository productRepository = new InMemoryProductRepository(); 
    
    ProductService productService = new ProductService(productRepository);

    ProductConsoleUI productConsoleUI = new ProductConsoleUI(productService, scanner);
    MainConsoleUI mainConsoleUI = new MainConsoleUI(productConsoleUI, scanner);
    
    mainConsoleUI.Mainstart();
  }
}
