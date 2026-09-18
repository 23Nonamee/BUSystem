// Packages
package com.busystem.infrastructure.product;

import java.util.ArrayList;
// Libraries
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;

import com.busystem.domain.product.Product;
import com.busystem.domain.product.ProductRepository;

/**
 * InMemoryProductRepository
 */
public class InMemoryProductRepository implements ProductRepository {
  
  private Map<String, Product> productMap = new HashMap<>();
    
  @Override
  public void save(Product product){
    productMap.put(product.getId(), product);
  }

  @Override
  public Optional<Product> findById(String id){

    return Optional.ofNullable(productMap.get(id));

  }

  @Override
  public List<Product> findAll(){
    return new ArrayList<>(productMap.values());
      
  }

}
