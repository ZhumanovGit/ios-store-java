package ru.mirea.ios.store.iosstore.app.inbound.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.ios.store.iosstore.adapter.persistence.EmployeeRepository;
import ru.mirea.ios.store.iosstore.adapter.persistence.OrderRepository;
import ru.mirea.ios.store.iosstore.app.exeption.NotFoundException;
import ru.mirea.ios.store.iosstore.app.exeption.PrivilegesException;
import ru.mirea.ios.store.iosstore.domain.category.Category;
import ru.mirea.ios.store.iosstore.domain.order.Order;
import ru.mirea.ios.store.iosstore.domain.product.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.mirea.ios.store.iosstore.domain.employee.enums.Position.ADMIN;

@Service
@RequiredArgsConstructor
public class GetPopularCategoriesInbound {
    private final OrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;

    public Set<Category> handleRequest(String userId, LocalDate start, LocalDate end) {
        checkEmployeeRole(userId);

        var orders = orderRepository.findAllByOrderDateIsAfterAndOrderDateIsBefore(start, end);

        return orders.stream()
                .map(Order::getProducts)
                .flatMap(List::stream)
                .map(Product::getCategory)
                .collect(Collectors.toSet());
    }

    private void checkEmployeeRole(String userId) {
        var employee = employeeRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Employee with id = " + userId + " was not found"));

        if (employee.getPosition() != ADMIN) {
            throw new PrivilegesException("Employee with id = " + userId + " has no privileges for this move");
        }
    }
}
