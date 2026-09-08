package com.busystem.application;


import java.util.Optional;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import com.busystem.domain.Product;
import com.busystem.infrastructure.InMemoryProductRepository;

import java.math.BigDecimal;

/**
 * ProductServiceTest
 */
public class ProductServiceTest {
  private InMemoryProductRepository repository;
  private ProductService productService;
  
  @BeforeEach
    void setUp() {
        repository = new InMemoryProductRepository();
        productService = new ProductService(repository);
      
        
    }

  @Test
  @DisplayName("Testing Register a product and find by id")
  void registerProduct(){
    
    String name = "laptop";
    String id = "P-101";
    BigDecimal price = new BigDecimal("999.99");
    int stock = 10;

    Product p = new Product(name, id, price, stock);
    
    productService.registerProduct(p);
    
    Optional<Product> registered = productService.getProductById(id); // find by id
    assertEquals(p, registered.orElseThrow()); // compare the registry with the product
    
  } 
 
  @Test
  @DisplayName("List al products")
  void allProductListing(){
    
      assertTrue(productService.getAllProducts().isEmpty());

  }

  @Test
  @DisplayName("Increse, Decrease and Adjust Product Stock")
  void increaseStock(){
      
      String name = "laptop";
      String id = "P-101";
      BigDecimal price = new BigDecimal("999.99");
      int stock = 10;

      Product p = new Product(name, id, price, stock);
      productService.registerProduct(p);
      
      Product productResult = productService.getProductById(id).orElseThrow(); // Get the product values
 

//    TEST increase stock
      productService.increaseProductStock(id, 10);

      assertEquals(20, productResult.getStock());


//    TEST Decrease stock
      productService.decreaseProductStock(id, 10);

      assertEquals(10, productResult.getStock());


//    TEST adjust stock
      productService.adjustProductStock(id, 30);

      assertEquals(30, productResult.getStock());
  }


  @Test
  @DisplayName("Increase, Decrease and Adjust ERROR (cannotFoundById)")
  void cannotFoundById(){

    assertThrows(IllegalArgumentException.class,
        () -> productService.increaseProductStock("P-999", 10));

    assertThrows(IllegalArgumentException.class,
        () -> productService.decreaseProductStock("P-999", 10));
    
    assertThrows(IllegalArgumentException.class,
        () -> productService.adjustProductStock("P-999", 10));

    

  }






}
