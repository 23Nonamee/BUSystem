//Packages
package com.busystem.domain.product;

//Libraries
import java.util.List;
import java.util.Optional;


public interface ProductRepository {

  void save(Product product);

  Optional<Product> findById(String id);

  List<Product> findAll();
}
