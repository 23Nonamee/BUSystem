package com.busystem.application;

import com.busystem.domain.Sale;
import com.busystem.domain.SaleRepository;
import com.busystem.domain.SaleStatus;
import com.busystem.domain.SaleItem;
import com.busystem.domain.Product;
import com.busystem.domain.ProductRepository;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public class SaleService {
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;

    public SaleService(SaleRepository saleRepository, ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
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
    
    public void discountProductStockPaidSale(String saleId){

        Sale sale = saleRepository.findById(saleId).orElseThrow(
                                                  () -> new NoSuchElementException("No sale found with ID: " + saleId));
        
        if (sale.getSaleStatus() != SaleStatus.PAID){
            throw new IllegalStateException("Status is not pending. The stock cannot be modify.");

        }
        
        for (SaleItem item : sale.getSaleItemList()){

          String productId = item.getProduct().getId();
          int purchasedQuantity = item.getQuantity();
          

          Product productInInventory = productRepository.findById(productId)
                                              .orElseThrow( () -> new NoSuchElementException("Product not found with ID: " + productId) );


          productInInventory.decreaseStock(purchasedQuantity); 

          productRepository.save(productInInventory);
        }

        saleRepository.save(sale);
    }
}
