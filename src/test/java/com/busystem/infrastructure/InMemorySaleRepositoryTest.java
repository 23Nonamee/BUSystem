//packages
package com.busystem.infrastructure;

//libraries
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.busystem.domain.Sale;
import com.busystem.domain.SaleItem;
import com.busystem.domain.SaleStatus;


/**
 * InMemorySaleRepositoryTest
 */
public class InMemorySaleRepositoryTest {
  InMemorySaleRepository repository = new InMemorySaleRepository();

  @Test
  @DisplayName("Save and find a sale")
  void saveAndFindSale(){

    String saleId = "SUC1-001";
    LocalDateTime dateTime = LocalDateTime.now();
    SaleStatus saleStatus = SaleStatus.PENDING;
    List<SaleItem> items = new ArrayList<>();

    Sale sale = Sale.reconstituteSale(saleId, dateTime, saleStatus , items);

    repository.save(sale);

    Optional<Sale> result = repository.findById(saleId);

    assertTrue(result.isPresent());
    assertEquals(sale, result.get());
  }

  @Test
  @DisplayName("Non existent sale")
  void notExistent(){

    String saleId = "SUC1-999";

    Optional<Sale> result = repository.findById(saleId);

    assertFalse(result.isPresent());
  }

  @Test
  @DisplayName("Listing Sales")
  void Listing(){

    List<Sale> sales = repository.findAll();

    assertTrue(sales.isEmpty());
  }

  @Test
  @DisplayName("Save null sale")
  void saveNullSale(){

    assertThrows(NullPointerException.class,
            () -> repository.save(null));
  }
}
