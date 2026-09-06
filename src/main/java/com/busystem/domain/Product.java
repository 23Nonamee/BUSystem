package com.busystem.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

  private final String id;
  private String name;
  private BigDecimal price;
  private int stock;

  public Product(String name, String id, BigDecimal price, int stock) {
    this.name = Objects.requireNonNull(name, "Name cannot be empty");
    this.id = Objects.requireNonNull(id, "ID cannot be empty");
    
    if (price.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Price cannot be negative");
    } else {
      this.price = price;
    }

    if (stock < 0) {
      throw new IllegalArgumentException("Stock cannot be negative");
    } else {
      this.stock = stock;
    }
  }

  public String getName() {
    return name;
  }
    
  public String getId() {
    return id;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public int getStock() {
    return stock;
  }

  public void increaseStock(int amount){
    if (amount < 0) {
      throw new IllegalArgumentException("Stock cannot be negative");
    } else {
      this.stock += amount;
    }
  }

  public void decreaseStock(int amount){
    if (amount < 0) {
      throw new IllegalArgumentException("Stock cannot be negative");
    } else if (amount > this.stock) {
      throw new IllegalArgumentException("Amount cannot be greater than stock");
    } else {
      this.stock -= amount;
    }
  }

  public void adjustStock(int newRealStock) {
    if (newRealStock < 0) {
      throw new IllegalArgumentException("Stock cannot be negative");
    } else {
      this.stock = newRealStock;
    }
  }

}
