//packages
package com.busystem.infrastructure.order;

//libraries
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.busystem.domain.order.Order;
import com.busystem.domain.order.OrderItem;
import com.busystem.domain.order.OrderStatus;


/**
 * InMemoryOrderRepositoryTest
 */
public class InMemoryOrderRepositoryTest {
  InMemoryOrderRepository repository = new InMemoryOrderRepository();

  @Test
  @DisplayName("Save and find a order")
  void saveAndFindOrder(){

    String orderId = "SUC1-001";
    LocalDateTime dateTime = LocalDateTime.now();
    OrderStatus orderStatus = OrderStatus.PENDING;
    List<OrderItem> items = new ArrayList<>();

    Order order = Order.reconstituteOrder(orderId, dateTime, orderStatus, items);

    repository.save(order);

    Optional<Order> result = repository.findById(orderId);

    assertTrue(result.isPresent());
    assertEquals(order, result.get());
  }

  @Test
  @DisplayName("Non existent order")
  void notExistent(){

    String orderId = "SUC1-999";

    Optional<Order> result = repository.findById(orderId);

    assertFalse(result.isPresent());
  }

  @Test
  @DisplayName("Listing Orders")
  void Listing(){

    List<Order> orders = repository.findAll();

    assertTrue(orders.isEmpty());
  }

  @Test
  @DisplayName("Save null order")
  void saveNullorder(){

    assertThrows(NullPointerException.class,
            () -> repository.save(null));
  }
}
