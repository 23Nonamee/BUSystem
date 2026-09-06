//packages
package com.busystem.domain;

//libraries
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

 class ProductTest {

  @Test
  @DisplayName("Creating a product")
  void createProduct() {
    String name = "laptop";
    String id = "P-101";
    BigDecimal price = new BigDecimal("999.99");
    int stock = 10;
    
    Product p = new Product(name,id,price,stock);

    assertEquals(name, p.getName());
    assertEquals(id, p.getId());
    assertEquals(price, p.getPrice());
    assertEquals(stock, p.getStock());
  }

   
  @Test
  @DisplayName("Invalid stock")
  void invalidStock(){

    String name = "laptop";
    String id = "P-101";
    BigDecimal price = new BigDecimal("999.99");
    int stock = 10;
    
    Product p = new Product(name,id,price,stock);

    final int NEGATIVEAMOUNT = -11;
    final int GREATERAMOUNT = 11;

    assertThrows(
        IllegalArgumentException.class,  
        () -> p.increaseStock(NEGATIVEAMOUNT)
        );

    assertThrows(
        IllegalArgumentException.class,  
        () -> p.decreaseStock(NEGATIVEAMOUNT)
        );

    assertThrows(
        IllegalArgumentException.class,  
        () -> p.decreaseStock(GREATERAMOUNT)
        );

    assertThrows(
        IllegalArgumentException.class,  
        () -> p.adjustStock(NEGATIVEAMOUNT)
        );
  }


}
