package com.busystem.domain.order;

import com.busystem.domain.product.Product;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


class SaleTest {

    private String orderId;
    private LocalDateTime dateTime;
    private OrderStatus orderStatus;
    private BigDecimal price;
    private OrderItem orderItem;
    private Order order;
    private List<OrderItem> orderItemListTest;

    @BeforeEach
    void setUp() {
        orderId = "SUC1-26-09-11-00001";
        dateTime = LocalDateTime.now();
        orderStatus = OrderStatus.PENDING;
        price = new BigDecimal("999.99");

        Product product = new Product("Laptop", "P-101", price, 3);
        orderItem = OrderItem.createNewOrderItem(product, 3);

        orderItemListTest = new ArrayList<>();
        order = Order.createNewOrder(orderId, dateTime, orderItemListTest);
    }

    @Test
    @DisplayName("Get SaleID")
    void getSaleid(){
        assertEquals(orderId, order.getOrderId());
    }

    @Test
    @DisplayName("Get SaleItem")
    void getSaleItemTest(){
        order.addItem(orderItem);
        assertEquals(orderItemListTest, order.getOrderItemList());
    }

    @Test
    @DisplayName("Get LocalTimeDate")
    void getLocalTimeDate(){
        assertEquals(dateTime, order.getDateTime());
    }

    @Test
    @DisplayName("Get SaleStatus")
    void getSaleStatus(){
        assertEquals(orderStatus, order.getOrderStatus());
    }

    @Test
    @DisplayName("Add an item to sale")
    void addItemSaleTest(){
        order.addItem(orderItem);
        assertEquals(orderItemListTest, order.getOrderItemList());

    }

    @Test
    @DisplayName("Calculate total")
    void getCalculateTotal(){

        BigDecimal realPrice = price.multiply(new BigDecimal(3));
        order.addItem(orderItem);
        assertEquals(realPrice, order.calculateTotal());

    }

    @Test
    @DisplayName("Mark sale status as paid")
    void markAsPaid(){

        order.markAsPaid();
        assertEquals(OrderStatus.PAID, order.getOrderStatus());
        

    }

    @Test
    @DisplayName("Mark sale status as cancelled")
    void markAsCancelled(){

        order.markAsCancelled();
        assertEquals(OrderStatus.CANCELLED, order.getOrderStatus());
        

    }
    
    @Test
    @DisplayName("try to mark a not pending sale as paid")
    void notPendingSaleAsPaid(){
      
      order.markAsCancelled();
      
      assertThrows(IllegalStateException.class,
                    () -> order.markAsPaid());

    }

    @Test
    @DisplayName("try to mark a not pending sale as cancelled")
    void notPendingSaleAsCancelled(){

      order.markAsPaid();
      

      assertThrows(IllegalStateException.class,
                    ()-> order.markAsCancelled());

    }


}





