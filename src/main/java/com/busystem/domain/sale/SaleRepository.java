package com.busystem.domain.sale;

import com.busystem.domain.sale.Sale;


import java.util.List;
import java.util.Optional;

public interface SaleRepository {

    void save(Sale sale);

    Optional<Sale> findById(String id);

    List<Sale> findAll();
}
