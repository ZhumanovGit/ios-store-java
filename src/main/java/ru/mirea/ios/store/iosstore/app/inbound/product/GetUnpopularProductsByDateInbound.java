package ru.mirea.ios.store.iosstore.app.inbound.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.ios.store.iosstore.adapter.persistence.OrderRepository;
import ru.mirea.ios.store.iosstore.adapter.persistence.ProductRepository;
import ru.mirea.ios.store.iosstore.domain.order.Order;
import ru.mirea.ios.store.iosstore.domain.product.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetUnpopularProductsByDateInbound {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public List<Product> handleRequest() {
        var orders = orderRepository.findAllByOrderDateIsAfter(findNeedMonth());

        if (orders.isEmpty()) {
            return productRepository.findAll();
        }
        Set<String> popularProductIds = orders.stream()
                .map(Order::getProducts)
                .flatMap(List::stream)
                .map(Product::getId)
                .collect(Collectors.toSet());
        return productRepository.findAllByIdIsNotIn(popularProductIds);

    }

    private LocalDate findNeedMonth() {
        var now = LocalDate.now();
        return now.minusMonths(1L);
    }
}
