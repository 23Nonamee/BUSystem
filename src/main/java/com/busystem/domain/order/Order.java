package com.busystem.domain.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Order {
    private final List<OrderItem> orderItemList;
    private final String orderId;
    private final LocalDateTime dateTime;
    private OrderStatus orderStatus;

    private Order(String orderId, LocalDateTime dateTime, OrderStatus orderStatus, List<OrderItem> orderItemList) {
        this.orderId = Objects.requireNonNull(orderId, "SaleID cannot be null");
        this.dateTime = Objects.requireNonNull(dateTime, "DateTime cannot be null");
        this.orderStatus = orderStatus;
        this.orderItemList = Objects.requireNonNull(orderItemList, "Sale itemlist cannot be null");
    }
    
    public static Order createNewOrder(String orderId, LocalDateTime dateTime, List<OrderItem> orderItemList){
        
        return new Order(orderId, dateTime, OrderStatus.PENDING, orderItemList);
    }

    
    public static Order reconstituteOrder(String orderId, LocalDateTime dateTime, OrderStatus orderStatus, List<OrderItem> orderItemList){

        return new Order(orderId, dateTime, orderStatus, orderItemList);
    }






    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public String getOrderId(){
            return orderId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void addItem(OrderItem item){
        Objects.requireNonNull(item, "SaleItem cannot be null");
        this.orderItemList.add(item);
    }

    public BigDecimal calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItemList) {
            total = total.add(orderItem.getSubTotal());
        }
        return total;
    }

    public void markAsPaid(){
        if (!(orderStatus == OrderStatus.PENDING)) {
           throw new IllegalStateException("Cannot change a sale status that is not pending"); 
        }
        this.orderStatus = OrderStatus.PAID;
    }

    public void markAsCancelled(){
        if (!(orderStatus == OrderStatus.PENDING)) {
           throw new IllegalStateException("Cannot change a sale status that is not pending"); 
        }
        this.orderStatus = OrderStatus.CANCELLED;

    }

}
