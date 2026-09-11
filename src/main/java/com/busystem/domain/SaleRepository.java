package com.busystem.domain;


import java.util.List;
import java.util.Optional;

public interface SaleRepository {

    void save(Sale sale);

    Optional<Sale> findSaleById(String id);

    List<Sale> findAllSale();
}
