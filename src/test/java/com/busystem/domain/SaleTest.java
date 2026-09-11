package com.busystem.domain;

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
    private SaleStatus saleStatus;
    private BigDecimal price;
    private SaleItem saleItem;
    private Sale sale;
    private List<SaleItem> saleItemListTest;

    @BeforeEach
    void setUp() {
        saleId = "SUC1-26-09-11-00001";
        dateTime = LocalDateTime.now();
        saleStatus = SaleStatus.PENDING;
        price = new BigDecimal("999.99");

        Product product = new Product("Laptop", "P-101", price, 3);
        saleItem = new SaleItem(product, 3, price);

        saleItemListTest = new ArrayList<>();
        sale = new Sale(saleId, dateTime, saleStatus, saleItemListTest);
    }

    @Test
    @DisplayName("Get SaleID")
    void getSaleid(){
        assertEquals(saleId ,sale.getSaleId());
    }

    @Test
    @DisplayName("Get SaleItem")
    void getSaleItemTest(){
        sale.addItem(saleItem);
        assertEquals(saleItemListTest,sale.getSaleItemList());
    }

    @Test
    @DisplayName("Get LocalTimeDate")
    void getLocalTimeDate(){
        assertEquals(dateTime, sale.getDateTime());
    }

    @Test
    @DisplayName("Get SaleStatus")
    void getSaleStatus(){
        assertEquals(saleStatus, sale.getSaleStatus());
    }

    @Test
    @DisplayName("Add an item to sale")
    void addItemSaleTest(){
        sale.addItem(saleItem);
        assertEquals(saleItemListTest, sale.getSaleItemList());

    }

    @Test
    @DisplayName("Calculate total")
    void getCalculateTotal(){

        BigDecimal realPrice = price.multiply(new BigDecimal(3));
        sale.addItem(saleItem);
        assertEquals(realPrice, sale.calculateTotal());

    }


}





