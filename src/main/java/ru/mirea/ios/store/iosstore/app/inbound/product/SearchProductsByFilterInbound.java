package ru.mirea.ios.store.iosstore.app.inbound.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.ios.store.iosstore.adapter.persistence.ProductRepository;
import ru.mirea.ios.store.iosstore.domain.product.Product;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchProductsByFilterInbound {
    private final ProductRepository productRepository;

    public List<Product> handleRequest(String searchString) {
        return productRepository.findAllByTitleStartingWithIgnoreCase(searchString.toLowerCase());
    }
}
