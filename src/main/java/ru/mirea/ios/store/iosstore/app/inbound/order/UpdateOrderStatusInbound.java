package ru.mirea.ios.store.iosstore.app.inbound.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.ios.store.iosstore.adapter.persistence.EmployeeRepository;
import ru.mirea.ios.store.iosstore.adapter.persistence.OrderRepository;
import ru.mirea.ios.store.iosstore.app.exeption.NotFoundException;
import ru.mirea.ios.store.iosstore.app.exeption.PrivilegesException;
import ru.mirea.ios.store.iosstore.domain.employee.enums.Position;
import ru.mirea.ios.store.iosstore.domain.order.Order;
import ru.mirea.ios.store.iosstore.domain.order.enums.Status;

import java.util.List;

import static ru.mirea.ios.store.iosstore.domain.employee.enums.Position.ADMIN;
import static ru.mirea.ios.store.iosstore.domain.employee.enums.Position.MANAGER;

@Service
@RequiredArgsConstructor
public class UpdateOrderStatusInbound {
    private final OrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;

    private static final List<Position> POSITION_FILTER = List.of(ADMIN, MANAGER);

    public Order handleRequest(String orderId, String employeeId, Status status) {
        checkEmployeeRole(employeeId);

        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order with id = "+ orderId + " was not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }

    private void checkEmployeeRole(String employeeId) {
        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee with id = " + employeeId + " was not found"));

        if (!POSITION_FILTER.contains(employee.getPosition())) {
            throw new PrivilegesException("Employee with id = " + employeeId + " has no privileges for this move");
        }
    }
}
