package com.busystem.infrastructure.payment;

import com.busystem.domain.payment.PaymentGateway;
import com.busystem.domain.payment.PaymentResponse;
import com.busystem.domain.payment.PaymentStatus;
import java.math.BigDecimal;
import java.util.UUID;

public class FakePaymentGateway implements PaymentGateway {

    private boolean shouldSuccess = true;
    
    public boolean setShouldSuccess(boolean shouldSuccess){

       return this.shouldSuccess = shouldSuccess;
    }

    @Override
    public PaymentResponse processTransaction(String orderId, BigDecimal amount) {

       if (!shouldSuccess){
         return new PaymentResponse(PaymentStatus.DECLINED, UUID.randomUUID().toString());

       }
       return new PaymentResponse(PaymentStatus.ACCEPTED, UUID.randomUUID().toString());
    }
 }

