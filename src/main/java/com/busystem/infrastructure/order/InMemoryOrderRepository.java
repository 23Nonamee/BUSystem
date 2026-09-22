package com.busystem.infrastructure.sale;

import com.busystem.domain.order.Order;
import com.busystem.domain.order.SaleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

public class InMemorySaleRepository implements SaleRepository {
    private final Map<String, Order> saleMap = new HashMap<>();


    @Override
    public void save(Order sale) {
        Objects.requireNonNull(sale, "Sale cannot be null");
        saleMap.put(sale.getOrderId(),sale);
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
