package com.busystem.application.payment;


import com.busystem.domain.payment.PaymentGateway;
import com.busystem.domain.payment.PaymentResponse;
import com.busystem.domain.payment.PaymentStatus;
import com.busystem.infrastructure.payment.FakePaymentGateway;
import com.busystem.domain.order.OrderRepository;
import com.busystem.domain.order.Order;



import java.math.BigDecimal;
import java.util.UUID;




/**
 * PaymentService
 */
public class PaymentService {

  private PaymentGateway paymentGateway;
  private OrderRepository orderRepository;


  public PaymentService(OrderRepository orderRepository, PaymentGateway paymentGateway) {
      
      this.paymentGateway = paymentGateway;
      this.orderRepository = orderRepository;
      
  }
  
  public PaymentResponse processPayment(String orderId, BigDecimal amount){

      PaymentResponse response = paymentGateway.processTransaction(orderId, amount);
      
      if (!(response.getPaymentStatus() == PaymentStatus.ACCEPTED)){

          orderRepository.findById(orderId).orElseThrow().markAsCancelled();
          return response;
      }
      
      orderRepository.findById(orderId).orElseThrow().markAsPaid();
      return response;          
      
  }





}
