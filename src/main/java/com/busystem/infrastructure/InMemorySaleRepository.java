package com.busystem.infrastructure;

import com.busystem.domain.Sale;
import com.busystem.domain.SaleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

public class InMemorySaleRepository implements SaleRepository {
    private final Map<String, Sale> saleMap = new HashMap<>();


    @Override
    public void save(Sale sale) {
        Objects.requireNonNull(sale, "Sale cannot be null");
        saleMap.put(sale.getSaleId(),sale);
    }

    @Override
    public Optional<Sale> findById(String id) {
        return Optional.ofNullable(saleMap.get(id));
    }

    @Override
    public List<Sale> findAll(){
        return new ArrayList<>(saleMap.values());
    }


}
