package com.busystem.domain.payment;




/**
 * PaymentResponse
 */
public class PaymentResponse {

  private final PaymentStatus paymentStatus;
  private final String transactionId;


  public PaymentResponse(PaymentStatus paymentStatus, String transactionId){
    this.paymentStatus = paymentStatus;
    this.transactionId = transactionId;

  }
  
  public String getTransactionId(){
    return this.transactionId;
  }


  public PaymentStatus getPaymentStatus(){
    return this.paymentStatus;  
  }


}
