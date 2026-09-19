package com.busystem.domain.payment;


import java.math.BigDecimal;

public interface PaymentGateway {

  PaymentStatus processPayment (String referenceId, String saleId, BigDecimal amount );
  
  
  

  

}
