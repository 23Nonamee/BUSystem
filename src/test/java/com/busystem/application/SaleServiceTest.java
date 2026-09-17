package com.busystem.application;


import com.busystem.domain.Sale;
import com.busystem.domain.SaleRepository;
import com.busystem.domain.SaleStatus;
import com.busystem.infrastructure.InMemoryProductRepository;
import com.busystem.infrastructure.InMemorySaleRepository;


import com.busystem.domain.SaleItem;
import com.busystem.domain.Product;
import com.busystem.domain.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
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
    private static Sale sale;
    private List<SaleItem> saleItemListTest;
    private Product product;
   

    @BeforeEach
    void setUp(){
       
        saleRepository = new InMemorySaleRepository();

        productRepository = new InMemoryProductRepository();

        saleService  = new SaleService(saleRepository, productRepository);


        product = new Product("laptop", "p-101", new BigDecimal("999.99"), 10);
        productRepository.save(product);


        saleItemListTest = new ArrayList<>();
        sale = Sale.createNewSale("SUC1-26-09-11-00001", LocalDateTime.now(), saleItemListTest);


        

    }


    @Test
    @DisplayName("Test save a sale and find by id")
    void saveAndFindSaleTest(){
      
      saleService.saveSale(sale);

      Optional<Sale> findSavedSale = saleService.getSaleByID("SUC1-26-09-11-00001");

      assertEquals(sale,findSavedSale.orElseThrow());

    }
    
    

    @Test
    @DisplayName("Test save a sale and find by id")
    void findAllSales(){
      
      saleService.saveSale(sale);
      
      List<Sale> findSavedSale = saleService.getAllSales();

      assertEquals(1, findSavedSale.size());
      assertEquals(sale, findSavedSale.get(0));
    }
    
    @Test
    @DisplayName("Discounting stock of a paid sale")
    void discountStockPaidSaleTest(){
      
      SaleItem saleItem = SaleItem.createNewSaleItem(product, 2);
      saleItemListTest = new ArrayList<>();
      
      
      sale = Sale.createNewSale("SUC1-26-09-11-00001", LocalDateTime.now(), saleItemListTest);
      sale.addItem(saleItem);  
      
      saleService.saveSale(sale);
      sale.markAsPaid();

      saleService.discountProductStockPaidSale("SUC1-26-09-11-00001");
      
      assertEquals(8, product.getStock() );
      
    }
    @Test
    @DisplayName("Discounting stock of a paid sale")
    void discountStockPendingSaleTest(){
      
      SaleItem saleItem = SaleItem.createNewSaleItem(product, 2);
      saleItemListTest = new ArrayList<>();
      
      
      sale = Sale.createNewSale("SUC1-26-09-11-00001", LocalDateTime.now(), saleItemListTest);
      sale.addItem(saleItem);  
      
      saleService.saveSale(sale);
      

      
      
      assertThrows(IllegalStateException.class,
                    () -> saleService.discountProductStockPaidSale("SUC1-26-09-11-00001"));
      
    }





  
  
}
