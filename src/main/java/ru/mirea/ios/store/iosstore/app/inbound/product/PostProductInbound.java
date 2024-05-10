package ru.mirea.ios.store.iosstore.app.inbound.product;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.ios.store.iosstore.adapter.persistence.CategoryRepository;
import ru.mirea.ios.store.iosstore.adapter.persistence.EmployeeRepository;
import ru.mirea.ios.store.iosstore.adapter.persistence.ProductRepository;
import ru.mirea.ios.store.iosstore.adapter.rest.product.mapper.ProductMapper;
import ru.mirea.ios.store.iosstore.adapter.rest.product.model.NewProductRequest;
import ru.mirea.ios.store.iosstore.app.exeption.NotFoundException;
import ru.mirea.ios.store.iosstore.app.exeption.PrivilegesException;
import ru.mirea.ios.store.iosstore.domain.category.Category;
import ru.mirea.ios.store.iosstore.domain.employee.Employee;
import ru.mirea.ios.store.iosstore.domain.employee.enums.Position;
import ru.mirea.ios.store.iosstore.domain.product.Product;

import java.util.List;

import static ru.mirea.ios.store.iosstore.domain.employee.enums.Position.ADMIN;
import static ru.mirea.ios.store.iosstore.domain.employee.enums.Position.MANAGER;

@Service
@RequiredArgsConstructor
public class PostProductInbound {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final EmployeeRepository employeeRepository;

    private static final List<Position> POSITION_FILTER = List.of(ADMIN, MANAGER);

    public Product handleRequest(NewProductRequest body, String employeeId) {
        checkEmployeePosition(employeeId);
        var category = categoryRepository.findById(body.categoryId())
                .orElseThrow(() -> new NotFoundException("Category with id = " + body.categoryId() + " was not found"));
        var product = Product.builder()
                .title(body.title())
                .price(body.price())
                .description(body.description())
                .publisher(body.publisher())
                .category(category)
                .build();
        return productRepository.save(product);


    }

    private void checkEmployeePosition(String employeeId) {
        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee with id = " + employeeId + " was not found"));

        if (!POSITION_FILTER.contains(employee.getPosition())) {
            throw new PrivilegesException("Employee with id = " + employeeId + " has not privileges for this move");
        }
    }
}
