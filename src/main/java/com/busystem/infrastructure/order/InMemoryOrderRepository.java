package com.busystem.infrastructure.order;

import com.busystem.domain.order.Order;
import com.busystem.domain.order.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

public class InMemoryOrderRepository implements OrderRepository {
    private final Map<String, Order> saleMap = new HashMap<>();


    @Override
    public void save(Order order) {
        Objects.requireNonNull(order, "Sale cannot be null");
        saleMap.put(order.getOrderId(), order);
    }

    @Override
    public Optional<Order> findById(String id) {
        return Optional.ofNullable(saleMap.get(id));
    }

    @Override
    public List<Order> findAll(){
        return new ArrayList<>(saleMap.values());
    }


}
