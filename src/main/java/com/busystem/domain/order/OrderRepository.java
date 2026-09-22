package com.busystem.domain.order;


import java.util.List;
import java.util.Optional;

public interface SaleRepository {

    void save(Order sale);

    Optional<Order> findById(String id);

    List<Order> findAll();
}
