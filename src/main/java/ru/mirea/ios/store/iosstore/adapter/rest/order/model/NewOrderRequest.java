package ru.mirea.ios.store.iosstore.adapter.rest.order.model;

import java.util.List;

public record NewOrderRequest(List<String> productsIds, String customerId) {
}
