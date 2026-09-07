//packages
package com.busystem.infrastructure;

//libraries
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.List;

import com.busystem.domain.Product;


/**
 * InMemoryProductRepositoryTest
 */
public class InMemoryProductRepositoryTest {
  InMemoryProductRepository repository = new InMemoryProductRepository(); 

  @Test
  @DisplayName("Save and find a product")
  void saveAndFindProduct(){


    String name = "laptop";
    String id = "P-101";
    BigDecimal price = new BigDecimal("999.99");
    int stock = 10;
    
    Product p = new Product(name,id,price,stock);

    repository.save(p);
    
    Optional<Product> result = repository.findById(id);
      
      assertTrue(result.isPresent());
      assertEquals(p, result.get());    
  }



  @Test
  @DisplayName("Non existent product")
  void notExistent(){

    String id = "P-999";

    Optional<Product> result = repository.findById(id);
      
      assertFalse(result.isPresent());
   
  }
  
  @Test
  @DisplayName("Listing Products")
  void Listing(){
    
    List<Product> products = repository.findAll();

    assertTrue(products.isEmpty());

    
  }



}
