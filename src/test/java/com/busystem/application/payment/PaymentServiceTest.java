package com.busystem.application.payment;

import com.busystem.domain.order.Order;
import com.busystem.domain.order.OrderItem;
import com.busystem.domain.order.OrderRepository;
import com.busystem.domain.order.OrderStatus;
import com.busystem.domain.payment.PaymentResponse;
import com.busystem.domain.payment.PaymentGateway;
import com.busystem.domain.payment.PaymentStatus;
import com.busystem.infrastructure.payment.FakePaymentGateway;
import com.busystem.application.payment.PaymentService;
import com.busystem.domain.product.Product;
import com.busystem.infrastructure.order.InMemoryOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;






/**
 * PaymentServiceTest
 */
public class PaymentServiceTest {
    private String orderId;
    private LocalDateTime dateTime;
    private OrderStatus orderStatus;
    private BigDecimal price;
    private OrderItem orderItem;
    private Order order;
    private List<OrderItem> orderItemListTest;
    private PaymentGateway paymentGateway;
    private OrderRepository orderRepository;
    private PaymentService paymentService;


    @BeforeEach
    void setUp(){
        paymentGateway = new FakePaymentGateway();
        orderRepository = new InMemoryOrderRepository();

        orderId = "SUC1-26-09-11-00001";
        dateTime = LocalDateTime.now();
        orderStatus = OrderStatus.PENDING;
        price = new BigDecimal("999.99");

        Product product = new Product("Laptop", "P-101", price, 3);
        orderItem = OrderItem.createNewOrderItem(product, 3);

        orderItemListTest = new ArrayList<>();
        orderItemListTest.add(orderItem);

        order = Order.createNewOrder(orderId, dateTime, orderItemListTest);

        orderRepository.save(order);

        paymentService = new PaymentService(orderRepository, paymentGateway);
    }

    

    @Test
    @DisplayName("Testing successful payment")
    void testProcessPaymentSuccess(){
        PaymentResponse response = paymentService.processPayment(orderId, order.calculateTotal() );
        assertEquals(PaymentStatus.ACCEPTED, response.getPaymentStatus());


        assertEquals(OrderStatus.PAID, orderRepository.findById(orderId).orElseThrow().getOrderStatus());

    }

    @Test
    @DisplayName("Testing failed payment")
    void testProcessPaymentFail(){
        ((FakePaymentGateway) paymentGateway).setShouldSuccess(false);
        PaymentResponse response = paymentService.processPayment(orderId, order.calculateTotal());
        assertEquals(PaymentStatus.DECLINED, response.getPaymentStatus());


        assertEquals(OrderStatus.CANCELLED, orderRepository.findById(orderId).orElseThrow().getOrderStatus());

    }

    

}


