package ru.mirea.ios.store.iosstore.adapter.rest.product.model;

public record NewProductRequest(Double price, String categoryId, String publisher, String description, String title) {
}
