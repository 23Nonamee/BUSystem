package com.busystem.domain.payment;

import java.math.BigDecimal;


public interface PaymentGateway {

    PaymentResponse processTransaction(String orderId, BigDecimal amount);
}
