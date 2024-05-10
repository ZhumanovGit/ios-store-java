package ru.mirea.ios.store.iosstore.adapter.rest.order.model;

import ru.mirea.ios.store.iosstore.domain.order.enums.Status;
import ru.mirea.ios.store.iosstore.domain.product.Product;

import java.util.List;

public record OrderDto(Long id, String clientName, String employeeName, List<Product> products, Status status) {
}
