package com.busystem.application.sale;

import com.busystem.domain.order.Order;
import com.busystem.domain.order.SaleRepository;
import com.busystem.domain.order.OrderStatus;
import com.busystem.domain.order.OrderItem;
import com.busystem.domain.product.Product;
import com.busystem.domain.product.ProductRepository;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public class SaleService {
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;

    public SaleService(SaleRepository saleRepository, ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
    }

    public void saveSale(Order sale){
        Objects.requireNonNull(sale);
        saleRepository.save(sale);
    }

    public Optional<Order> getSaleByID(String id){


        return saleRepository.findById(id);
    }

    public List<Order> getAllSales(){

        return saleRepository.findAll();
    }
    
    public void discountProductStockPaidSale(String saleId){

        Order sale = saleRepository.findById(saleId).orElseThrow(
                                                  () -> new NoSuchElementException("No sale found with ID: " + saleId));
        
        if (sale.getOrderStatus() != OrderStatus.PAID){
            throw new IllegalStateException("Status is not pending. The stock cannot be modify.");

        }
        
        for (OrderItem item : sale.getOrderItemList()){

          String productId = item.getProduct().getId();
          int purchasedQuantity = item.getQuantity();
          

          Product productInInventory = productRepository.findById(productId)
                                              .orElseThrow( () -> new NoSuchElementException("Product not found with ID: " + productId) );


          productInInventory.decreaseStock(purchasedQuantity); 

          productRepository.save(productInInventory);
        }

        saleRepository.save(sale);
    }
}
