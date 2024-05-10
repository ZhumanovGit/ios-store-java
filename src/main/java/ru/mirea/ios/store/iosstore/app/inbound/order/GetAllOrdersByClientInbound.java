package ru.mirea.ios.store.iosstore.app.inbound.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.ios.store.iosstore.adapter.persistence.ClientRepository;
import ru.mirea.ios.store.iosstore.adapter.persistence.OrderRepository;
import ru.mirea.ios.store.iosstore.app.exeption.NotFoundException;
import ru.mirea.ios.store.iosstore.domain.order.Order;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllOrdersByClientInbound {
    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;

    public List<Order> handleRequest(String userId) {
        checkUser(userId);

        return orderRepository.findAllByCustomerId(userId);
    }

    private void checkUser(String userId) {
        clientRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Client with id = " + userId + " was not found"));
    }
}
