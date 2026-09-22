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
 * InMemorySaleRepositoryTest
 */
public class InMemoryOrderRepositoryTest {
  InMemoryOrderRepository repository = new InMemoryOrderRepository();

  @Test
  @DisplayName("Save and find a sale")
  void saveAndFindSale(){

    String saleId = "SUC1-001";
    LocalDateTime dateTime = LocalDateTime.now();
    OrderStatus orderStatus = OrderStatus.PENDING;
    List<OrderItem> items = new ArrayList<>();

    Order sale = Order.reconstituteOrder(saleId, dateTime, orderStatus, items);

    repository.save(sale);

    Optional<Order> result = repository.findById(saleId);

    assertTrue(result.isPresent());
    assertEquals(sale, result.get());
  }

  @Test
  @DisplayName("Non existent sale")
  void notExistent(){

    String saleId = "SUC1-999";

    Optional<Order> result = repository.findById(saleId);

    assertFalse(result.isPresent());
  }

  @Test
  @DisplayName("Listing Sales")
  void Listing(){

    List<Order> sales = repository.findAll();

    assertTrue(sales.isEmpty());
  }

  @Test
  @DisplayName("Save null sale")
  void saveNullSale(){

    assertThrows(NullPointerException.class,
            () -> repository.save(null));
  }
}
