package com.busystem.ui.cli;

import java.util.Scanner;



/**
 * MainConsoleUI
 */
public class MainConsoleUI {
  private final Scanner scanner;
  private ProductConsoleUI productConsoleUI;
  
  public MainConsoleUI(ProductConsoleUI productConsoleUI, Scanner scanner){
    this.scanner = scanner;
    this.productConsoleUI = productConsoleUI;
  } 

  public void Mainstart(){
    int option = -1; 
    while (!(option == 0)) {
      printMenu();
      option = Integer.parseInt(scanner.nextLine().trim());


      switch (option) {
        case 1 -> productConsoleUI.start(); 
        case 0 -> System.out.println("Exit..");


        default -> System.out.println("Invalid option, try again");
      } 
    }  
  }
  

  private void printMenu() {
    

    System.out.println("=================================");
    System.out.println("  🏢 BUSystem - ERP Empresarial   ");
    System.out.println("=================================");
    System.out.println("1) Products Module");
    System.out.println("2) Comming soon");
    System.out.println("3) Comming soon");
    System.out.println("4) Comming soon");
    System.out.println("0) Exit");
    System.out.println("=================================");
  } 








}
