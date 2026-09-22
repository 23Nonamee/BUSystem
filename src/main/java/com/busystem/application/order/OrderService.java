package com.busystem.application.order;

import com.busystem.domain.order.Order;
import com.busystem.domain.order.OrderRepository;
import com.busystem.domain.order.OrderStatus;
import com.busystem.domain.order.OrderItem;
import com.busystem.domain.product.Product;
import com.busystem.domain.product.ProductRepository;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public void saveOrder(Order order){
        Objects.requireNonNull(order);
        orderRepository.save(order);
    }

    public Optional<Order> getOrderByID(String id){


        return orderRepository.findById(id);
    }

    public List<Order> getAllOrders(){

        return orderRepository.findAll();
    }
    
    public void discountProductStockPaidOrder(String orderId){

        Order order = orderRepository.findById(orderId).orElseThrow(
                                                  () -> new NoSuchElementException("No order found with ID: " + orderId));
        
        if (order.getOrderStatus() != OrderStatus.PAID){
            throw new IllegalStateException("Status is not pending. The stock cannot be modify.");

        }
        
        for (OrderItem item : order.getOrderItemList()){

          String productId = item.getProduct().getId();
          int purchasedQuantity = item.getQuantity();
          

          Product productInInventory = productRepository.findById(productId)
                                              .orElseThrow( () -> new NoSuchElementException("Product not found with ID: " + productId) );


          productInInventory.decreaseStock(purchasedQuantity); 

          productRepository.save(productInInventory);
        }

        orderRepository.save(order);
    }
}
