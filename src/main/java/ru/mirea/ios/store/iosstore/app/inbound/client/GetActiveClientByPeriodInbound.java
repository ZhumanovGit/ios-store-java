package ru.mirea.ios.store.iosstore.app.inbound.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.ios.store.iosstore.adapter.persistence.ClientRepository;
import ru.mirea.ios.store.iosstore.adapter.persistence.EmployeeRepository;
import ru.mirea.ios.store.iosstore.adapter.persistence.OrderRepository;
import ru.mirea.ios.store.iosstore.app.exeption.NotFoundException;
import ru.mirea.ios.store.iosstore.app.exeption.PrivilegesException;
import ru.mirea.ios.store.iosstore.domain.client.Client;
import ru.mirea.ios.store.iosstore.domain.employee.enums.Position;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Map.Entry;

@Service
@RequiredArgsConstructor
public class GetActiveClientByPeriodInbound {
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final OrderRepository orderRepository;

    public List<Client> handleRequest(String userId, Long ordersCount) {
        checkEmployeeRole(userId);

        var orders = orderRepository.findAll();
        var needIdsMap = orders.stream()
                .collect(Collectors.groupingBy(order -> order.getCustomer().getId(), Collectors.counting()));

        Set<String> needIds = new HashSet<>();
        for (Entry<String, Long> entry : needIdsMap.entrySet()) {
            if (entry.getValue() >= ordersCount) {
                needIds.add(entry.getKey());
            }
        }

        return clientRepository.findAllByIdIn(needIds);
    }

    private void checkEmployeeRole(String userId) {
        var employee = employeeRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Employee with id = " + userId + " was not found"));

        if (employee.getPosition() != Position.ADMIN) {
            throw new PrivilegesException("Employee with id = " + userId + " has no privileges for this move");
        }
    }
}
