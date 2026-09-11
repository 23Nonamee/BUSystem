package com.busystem.infrastructure;

import com.busystem.domain.Sale;
import com.busystem.domain.SaleRepository;
import com.busystem.domain.SaleStatus;

import java.util.*;

public class InMemorySaleRepository implements SaleRepository {
    private Map<String, Sale> saleMap = new HashMap<>();

    @Override
    public void save(Sale sale) {
        saleMap.put(sale.getSaleId(),sale);
    }

    @Override
    public Optional<Sale> findSaleById(String id) {
        return Optional.ofNullable(saleMap.get(id));
    }

    @Override
    public List<Sale> findAllSale(){
        return new ArrayList<>(saleMap.values());
    }
}
