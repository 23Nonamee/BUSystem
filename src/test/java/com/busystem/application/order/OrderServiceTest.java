package com.busystem.application.order;


import com.busystem.domain.order.Order;
import com.busystem.domain.order.OrderRepository;
import com.busystem.infrastructure.product.InMemoryProductRepository;
import com.busystem.infrastructure.order.InMemoryOrderRepository;


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
public class OrderServiceTest {
    private OrderService orderService;
    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private static Order order;
    private List<OrderItem> orderItemListTest;
    private Product product;
   

    @BeforeEach
    void setUp(){
       
        orderRepository = new InMemoryOrderRepository();

        productRepository = new InMemoryProductRepository();

        orderService = new OrderService(orderRepository, productRepository);


        product = new Product("laptop", "p-101", new BigDecimal("999.99"), 10);
        productRepository.save(product);


        orderItemListTest = new ArrayList<>();
        order = Order.createNewOrder("SUC1-26-09-11-00001", LocalDateTime.now(), orderItemListTest);


        

    }


    @Test
    @DisplayName("Test save a order and find by id")
    void saveAndFindOrderTest(){
      
      orderService.saveOrder(order);

      Optional<Order> findSavedOrder = orderService.getOrderByID("SUC1-26-09-11-00001");

      assertEquals(order, findSavedOrder.orElseThrow());

    }
    
    

    @Test
    @DisplayName("Test save a order and find by id")
    void findAllSales(){
      
      orderService.saveOrder(order);
      
      List<Order> findSavedSale = orderService.getAllOrders();

      assertEquals(1, findSavedSale.size());
      assertEquals(order, findSavedSale.get(0));
    }
    
    @Test
    @DisplayName("Discounting stock of a paid order")
    void discountStockPaidSaleTest(){
      
      OrderItem orderItem = OrderItem.createNewOrderItem(product, 2);
      orderItemListTest = new ArrayList<>();
      
      
      order = Order.createNewOrder("SUC1-26-09-11-00001", LocalDateTime.now(), orderItemListTest);
      order.addItem(orderItem);
      
      orderService.saveOrder(order);
      order.markAsPaid();

      orderService.discountProductStockPaidOrder("SUC1-26-09-11-00001");
      
      assertEquals(8, product.getStock() );
      
    }
    @Test
    @DisplayName("Discounting stock of a paid order")
    void discountStockPendingSaleTest(){
      
      OrderItem orderItem = OrderItem.createNewOrderItem(product, 2);
      orderItemListTest = new ArrayList<>();
      
      
      order = Order.createNewOrder("SUC1-26-09-11-00001", LocalDateTime.now(), orderItemListTest);
      order.addItem(orderItem);
      
      orderService.saveOrder(order);
      

      
      
      assertThrows(IllegalStateException.class,
                    () -> orderService.discountProductStockPaidOrder("SUC1-26-09-11-00001"));
      
    }





  
  
}
