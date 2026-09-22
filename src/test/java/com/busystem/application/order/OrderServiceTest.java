package com.busystem.application.sale;


import com.busystem.domain.order.Order;
import com.busystem.domain.order.SaleRepository;
import com.busystem.infrastructure.product.InMemoryProductRepository;
import com.busystem.infrastructure.sale.InMemorySaleRepository;


import com.busystem.domain.order.OrderItem;
import com.busystem.domain.product.Product;
import com.busystem.domain.product.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
    



/**
 * SaleServiceTest
 */
public class SaleServiceTest {
    private SaleService saleService;
    private SaleRepository saleRepository;
    private ProductRepository productRepository;
    private static Order sale;
    private List<OrderItem> orderItemListTest;
    private Product product;
   

    @BeforeEach
    void setUp(){
       
        saleRepository = new InMemorySaleRepository();

        productRepository = new InMemoryProductRepository();

        saleService  = new SaleService(saleRepository, productRepository);


        product = new Product("laptop", "p-101", new BigDecimal("999.99"), 10);
        productRepository.save(product);


        orderItemListTest = new ArrayList<>();
        sale = Order.createNewSale("SUC1-26-09-11-00001", LocalDateTime.now(), orderItemListTest);


        

    }


    @Test
    @DisplayName("Test save a sale and find by id")
    void saveAndFindSaleTest(){
      
      saleService.saveSale(sale);

      Optional<Order> findSavedSale = saleService.getSaleByID("SUC1-26-09-11-00001");

      assertEquals(sale,findSavedSale.orElseThrow());

    }
    
    

    @Test
    @DisplayName("Test save a sale and find by id")
    void findAllSales(){
      
      saleService.saveSale(sale);
      
      List<Order> findSavedSale = saleService.getAllSales();

      assertEquals(1, findSavedSale.size());
      assertEquals(sale, findSavedSale.get(0));
    }
    
    @Test
    @DisplayName("Discounting stock of a paid sale")
    void discountStockPaidSaleTest(){
      
      OrderItem orderItem = OrderItem.createNewOrderItem(product, 2);
      orderItemListTest = new ArrayList<>();
      
      
      sale = Order.createNewSale("SUC1-26-09-11-00001", LocalDateTime.now(), orderItemListTest);
      sale.addItem(orderItem);
      
      saleService.saveSale(sale);
      sale.markAsPaid();

      saleService.discountProductStockPaidSale("SUC1-26-09-11-00001");
      
      assertEquals(8, product.getStock() );
      
    }
    @Test
    @DisplayName("Discounting stock of a paid sale")
    void discountStockPendingSaleTest(){
      
      OrderItem orderItem = OrderItem.createNewOrderItem(product, 2);
      orderItemListTest = new ArrayList<>();
      
      
      sale = Order.createNewSale("SUC1-26-09-11-00001", LocalDateTime.now(), orderItemListTest);
      sale.addItem(orderItem);
      
      saleService.saveSale(sale);
      

      
      
      assertThrows(IllegalStateException.class,
                    () -> saleService.discountProductStockPaidSale("SUC1-26-09-11-00001"));
      
    }





  
  
}
