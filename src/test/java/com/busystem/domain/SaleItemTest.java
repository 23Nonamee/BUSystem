package com.busystem.domain;

import com.busystem.domain.sale.SaleItem;

import com.busystem.domain.sale.Sale;

import com.busystem.domain.product.Product;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

class SaleItemTest {
    String name = "Laptop";
    String id = "P-101";
    BigDecimal price = new BigDecimal("999.99");
    int stock = 10;

    Product product = new Product(name, id, price, stock);

    SaleItem saleItem = SaleItem.createNewSaleItem(product, 3);

    @Test
    @DisplayName("Get quantity")
    void getUnitPriceTest() {
        BigDecimal itemPrice = saleItem.getUnitPrice();

        assertEquals(price, itemPrice);
    }

    @Test
    @DisplayName("Get unit price")
    void getQuantityTest() {
        int itemQuantity = saleItem.getQuantity();

        assertEquals(3, itemQuantity);
    }

    @Test
    @DisplayName("Get subtotal SaleItem ")
    void getSubtotalTest() {
        BigDecimal subTotal = saleItem.getSubTotal();

        BigDecimal expectedSubTotal = new BigDecimal("2999.97");

        assertEquals(expectedSubTotal, subTotal);
    }

    @Test
    @DisplayName("Bad way: create a SaleItem with quantity negative or zero")
    void badQuantitySaleItemCreate() {
        int negativeQuantity = -1;
        int zeroQuantity = 0;

        assertThrows(IllegalArgumentException.class,
                () -> SaleItem.createNewSaleItem(product, negativeQuantity));

        assertThrows(IllegalArgumentException.class,
                () -> SaleItem.createNewSaleItem(product, zeroQuantity));

    }

}