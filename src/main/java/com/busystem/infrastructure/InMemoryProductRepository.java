// Packages
package com.busystem.infrastructure;

import java.util.ArrayList;
// Libraries
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;

import com.busystem.domain.Product;
import com.busystem.domain.ProductRepository;



/**
 * InMemoryProductRepository
 */
public class InMemoryProductRepository implements ProductRepository {
  
  private Map<String, Product> internalMap = new HashMap<>();
    
  @Override
  public void save(Product product){
    internalMap.put(product.getId(), product);
  }

  @Override
  public Optional<Product> findById(String id){

    return Optional.ofNullable(internalMap.get(id));

  }

  @Override
  public List<Product> findAll(){
    return new ArrayList<>(internalMap.values());
      
  }

}
