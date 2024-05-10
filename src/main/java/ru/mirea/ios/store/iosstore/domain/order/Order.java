package ru.mirea.ios.store.iosstore.domain.order;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.mirea.ios.store.iosstore.domain.client.Client;
import ru.mirea.ios.store.iosstore.domain.employee.Employee;
import ru.mirea.ios.store.iosstore.domain.order.enums.Status;
import ru.mirea.ios.store.iosstore.domain.product.Product;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    @DBRef
    private Client customer;
    @DBRef
    private Employee seller;
    @DBRef
    private List<Product> products;
    private double amount;
    private LocalDate orderDate;
    private Status status;
}
