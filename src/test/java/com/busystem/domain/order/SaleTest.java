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

    private String saleId;
    private LocalDateTime dateTime;
    private OrderStatus orderStatus;
    private BigDecimal price;
    private OrderItem orderItem;
    private Order sale;
    private List<OrderItem> orderItemListTest;

    @BeforeEach
    void setUp() {
        saleId = "SUC1-26-09-11-00001";
        dateTime = LocalDateTime.now();
        orderStatus = OrderStatus.PENDING;
        price = new BigDecimal("999.99");

        Product product = new Product("Laptop", "P-101", price, 3);
        orderItem = OrderItem.createNewOrderItem(product, 3);

        orderItemListTest = new ArrayList<>();
        sale = Order.createNewOrder(saleId, dateTime, orderItemListTest);
    }

    @Test
    @DisplayName("Get SaleID")
    void getSaleid(){
        assertEquals(saleId ,sale.getOrderId());
    }

    @Test
    @DisplayName("Get SaleItem")
    void getSaleItemTest(){
        sale.addItem(orderItem);
        assertEquals(orderItemListTest,sale.getOrderItemList());
    }

    @Test
    @DisplayName("Get LocalTimeDate")
    void getLocalTimeDate(){
        assertEquals(dateTime, sale.getDateTime());
    }

    @Test
    @DisplayName("Get SaleStatus")
    void getSaleStatus(){
        assertEquals(orderStatus, sale.getOrderStatus());
    }

    @Test
    @DisplayName("Add an item to sale")
    void addItemSaleTest(){
        sale.addItem(orderItem);
        assertEquals(orderItemListTest, sale.getOrderItemList());

    }

    @Test
    @DisplayName("Calculate total")
    void getCalculateTotal(){

        BigDecimal realPrice = price.multiply(new BigDecimal(3));
        sale.addItem(orderItem);
        assertEquals(realPrice, sale.calculateTotal());

    }

    @Test
    @DisplayName("Mark sale status as paid")
    void markAsPaid(){

        sale.markAsPaid();
        assertEquals(OrderStatus.PAID, sale.getOrderStatus());
        

    }

    @Test
    @DisplayName("Mark sale status as cancelled")
    void markAsCancelled(){

        sale.markAsCancelled();
        assertEquals(OrderStatus.CANCELLED, sale.getOrderStatus());
        

    }
    
    @Test
    @DisplayName("try to mark a not pending sale as paid")
    void notPendingSaleAsPaid(){
      
      sale.markAsCancelled();
      
      assertThrows(IllegalStateException.class,
                    () ->sale.markAsPaid());

    }

    @Test
    @DisplayName("try to mark a not pending sale as cancelled")
    void notPendingSaleAsCancelled(){

      sale.markAsPaid();
      

      assertThrows(IllegalStateException.class,
                    ()-> sale.markAsCancelled());

    }


}





