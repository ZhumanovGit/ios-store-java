package ru.mirea.ios.store.iosstore.app.inbound.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.ios.store.iosstore.adapter.persistence.EmployeeRepository;
import ru.mirea.ios.store.iosstore.adapter.persistence.OrderRepository;
import ru.mirea.ios.store.iosstore.app.exeption.NotFoundException;
import ru.mirea.ios.store.iosstore.app.exeption.PrivilegesException;
import ru.mirea.ios.store.iosstore.domain.employee.enums.Position;
import ru.mirea.ios.store.iosstore.domain.order.Order;

import java.time.LocalDate;
import java.util.List;

import static ru.mirea.ios.store.iosstore.domain.employee.enums.Position.ADMIN;
import static ru.mirea.ios.store.iosstore.domain.employee.enums.Position.MANAGER;

@Service
@RequiredArgsConstructor
public class GetBestOrdersForLastMonthInbound {
    private final OrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;

    private static final List<Position> POSITION_FILTER = List.of(ADMIN, MANAGER);

    public List<Order> handleRequest(String employeeId, Double amount, Integer productsCount) {
        checkEmployee(employeeId);

        var orders = orderRepository.findAllByOrderDateIsAfterAndAmountGreaterThan(findLastMonth(), amount);

        return orders.stream()
                .filter(order -> order.getProducts().size() > productsCount)
                .toList();
    }

    private void checkEmployee(String employeeId) {
        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee with id = "+ employeeId + " was not found"));

        if (!POSITION_FILTER.contains(employee.getPosition())) {
            throw new PrivilegesException("Employee with id = " + employeeId + " has no privileges for this move");
        }
    }

    private LocalDate findLastMonth() {
        return LocalDate.now().minusMonths(1L);
    }
}
