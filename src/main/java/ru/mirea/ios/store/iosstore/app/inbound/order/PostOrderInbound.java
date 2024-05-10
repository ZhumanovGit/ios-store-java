package ru.mirea.ios.store.iosstore.app.inbound.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.ios.store.iosstore.adapter.persistence.ClientRepository;
import ru.mirea.ios.store.iosstore.adapter.persistence.EmployeeRepository;
import ru.mirea.ios.store.iosstore.adapter.persistence.OrderRepository;
import ru.mirea.ios.store.iosstore.adapter.persistence.ProductRepository;
import ru.mirea.ios.store.iosstore.adapter.rest.order.model.NewOrderRequest;
import ru.mirea.ios.store.iosstore.app.exeption.NotFoundException;
import ru.mirea.ios.store.iosstore.app.exeption.PrivilegesException;
import ru.mirea.ios.store.iosstore.domain.client.Client;
import ru.mirea.ios.store.iosstore.domain.employee.Employee;
import ru.mirea.ios.store.iosstore.domain.employee.enums.Position;
import ru.mirea.ios.store.iosstore.domain.order.Order;
import ru.mirea.ios.store.iosstore.domain.order.enums.Status;
import ru.mirea.ios.store.iosstore.domain.product.Product;

import java.time.LocalDate;
import java.util.List;

import static ru.mirea.ios.store.iosstore.domain.employee.enums.Position.ADMIN;
import static ru.mirea.ios.store.iosstore.domain.employee.enums.Position.MANAGER;

@Service
@RequiredArgsConstructor
public class PostOrderInbound {
    private final OrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    private static final List<Position> POSITION_FILTER = List.of(ADMIN, MANAGER);

    public Order handleRequest(String employeeId, NewOrderRequest body) {
        var employee = checkEmployeeRole(employeeId);
        var client = checkClient(body.customerId());

        List<Product> products = productRepository.findAllByIdIn(body.productsIds());

        double amount = 0.0;
        for (Product product : products) {
            amount += product.getPrice();
        }

        var order = Order.builder()
                .orderDate(LocalDate.now())
                .customer(client)
                .seller(employee)
                .products(products)
                .amount(amount)
                .status(Status.NEW)
                .build();

        return orderRepository.save(order);
    }

    private Employee checkEmployeeRole(String employeeId) {
        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee with id = " + employeeId + " was not found"));

        if (!POSITION_FILTER.contains(employee.getPosition())) {
            throw new PrivilegesException("Employee with id = " + employeeId + " has not privileges for this move");
        }

        return employee;
    }

    private Client checkClient(String clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client with id = " + clientId + " was not found"));
    }
}
