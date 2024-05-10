package ru.mirea.ios.store.iosstore.app.inbound.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.ios.store.iosstore.adapter.persistence.ClientRepository;
import ru.mirea.ios.store.iosstore.adapter.persistence.OrderRepository;
import ru.mirea.ios.store.iosstore.adapter.persistence.ProductRepository;
import ru.mirea.ios.store.iosstore.app.exeption.ConflictException;
import ru.mirea.ios.store.iosstore.app.exeption.NotFoundException;
import ru.mirea.ios.store.iosstore.domain.client.Client;

@Service
@RequiredArgsConstructor
public class PostProductToOrderInbound {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;

    public void handleRequest(String orderId, String productId, String userId) {
        var client = checkUserRole(userId);
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product with id = " + productId + " is not found"));
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order with id = " + orderId + " is not found"));

        if (!order.getCustomer().equals(client)) {
            throw new ConflictException("user with id = " + userId + " is not owner of order with id = " + orderId);
        }
        var orderProducts = order.getProducts();
        orderProducts.add(product);
        order.setAmount(order.getAmount() + product.getPrice());
        order.setProducts(orderProducts);

        orderRepository.save(order);
    }

    private Client checkUserRole(String userId) {
        return clientRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User does not registered in system"));
    }
}
