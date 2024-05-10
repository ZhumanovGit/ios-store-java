package ru.mirea.ios.store.iosstore.adapter.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.mirea.ios.store.iosstore.domain.order.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findAllByOrderDateIsAfter(LocalDate date);

    List<Order> findAllByOrderDateIsAfterAndOrderDateIsBefore(LocalDate start, LocalDate end);

    List<Order> findAllByCustomerId(String customerId);

    List<Order> findAllByOrderDateIsAfterAndAmountGreaterThan(LocalDate date, Double amount);
}
