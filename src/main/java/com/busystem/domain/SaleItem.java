package com.busystem.domain;


import java.math.BigDecimal;
import java.util.Objects;

public class SaleItem {
    private final Product product;
    private final int quantity;
    private final BigDecimal unitPrice;

    public SaleItem(Product product, int quantity, BigDecimal unitPrice) {
        this.product = Objects.requireNonNull(product,"Product cannot be null");
        this.unitPrice = Objects.requireNonNull(unitPrice, "Unit price cannot be null");

        if (unitPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Unit price must be greater than zero");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        } else {
            this.quantity = quantity;
        }

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
