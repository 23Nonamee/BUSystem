package com.busystem.service;


import java.util.List;
import java.util.Optional;

import com.busystem.domain.Product;
import com.busystem.domain.ProductRepository;


/**
 * ProductService
 */
public class ProductService {
 
  private final ProductRepository productRepository; // create a variable type ProductRepository empty
  
//request a real ProductRepository Object
  public ProductService(ProductRepository productRepository){ 
//  save the real ProductRepository in the box
    this.productRepository = productRepository;    
  }

//register a product
  public void registerProduct(Product product) {

//  using the save method to add it in the productRepository
    productRepository.save(product);  
  } 



//get a product by id   
  public Optional<Product> getProductById(String id){

//  using the findById method to get the product
    return productRepository.findById(id);  
  } 



// obtain all existent products in productRepository
  public List<Product> getAllProducts(){

//  use the findAll method to see all the existent products
    return productRepository.findAll(); 
  }  

//modify quantity

  public void increaseProductStock(String productId, int quantity){
    
    Product product = productRepository.findById(productId)
      .orElseThrow(() -> new IllegalArgumentException("Not found a product with ID: "+productId));

      product.increaseStock(quantity);
      productRepository.save(product); 
  }
  public void decreaseProductStock(String productId, int quantity){
    
    Product product = productRepository.findById(productId)
      .orElseThrow(() -> new IllegalArgumentException("Not found a product with ID: "+productId));

      product.decreaseStock(quantity);
      productRepository.save(product); 
  }
  public void adjustProductStock(String productId, int quantity){
    
    Product product = productRepository.findById(productId)
      .orElseThrow(() -> new IllegalArgumentException("Not found a product with ID: "+productId));

      product.adjustStock(quantity);
      productRepository.save(product); 
  }


}
