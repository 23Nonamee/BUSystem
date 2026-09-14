package com.busystem.application;

import com.busystem.domain.Sale;
import com.busystem.domain.SaleRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SaleService {
    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public void saveSale(Sale sale){
        Objects.requireNonNull(sale);
        saleRepository.save(sale);
    }

    public Optional<Sale> getSaleByID(String id){


        return saleRepository.findById(id);
    }

    public List<Sale> getAllSales(){

        return saleRepository.findAll();
    }





}
