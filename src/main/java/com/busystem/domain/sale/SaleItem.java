package com.busystem.domain.sale;

import com.busystem.domain.sale.Sale;

import com.busystem.domain.product.Product;


import java.math.BigDecimal;
import java.util.Objects;

public class SaleItem {
    private final Product product;
    private final int quantity;
    private final BigDecimal unitPrice;

    private SaleItem(Product product, int quantity, BigDecimal price) {
        this.product = Objects.requireNonNull(product,"Product cannot be null");
        this.unitPrice = Objects.requireNonNull(price, "Unit price cannot be null");

        if (unitPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Unit price must be greater than zero");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        } else {
            this.quantity = quantity;
        }

    }

    public static SaleItem createNewSaleItem(Product product, int quantity){

        return new SaleItem(product, quantity, product.getPrice());

    }

    public static SaleItem reconstituteNewSaleItem(Product product, int quantity, BigDecimal historicalPrice){

        return new SaleItem(product, quantity, historicalPrice);

    }


    public Product getProduct (){
        return product;

    }


    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getSubTotal(){
        BigDecimal subTotal;
        subTotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
        return (subTotal);
    }
}
