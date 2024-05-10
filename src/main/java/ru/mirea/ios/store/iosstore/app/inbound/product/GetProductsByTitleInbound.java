package ru.mirea.ios.store.iosstore.app.inbound.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.ios.store.iosstore.adapter.persistence.ProductRepository;
import ru.mirea.ios.store.iosstore.app.exeption.NotFoundException;
import ru.mirea.ios.store.iosstore.domain.product.Product;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetProductsByTitleInbound {
    private final ProductRepository productRepository;

    public List<Product> handleRequest(String title) {
        var products = productRepository.findAllByTitle(title);

        if (products.isEmpty()) {
            throw new NotFoundException("products with title " + title + " was not found");
        }
        return products;
    }
}
