package ru.mirea.ios.store.iosstore.app.inbound.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.ios.store.iosstore.adapter.persistence.EmployeeRepository;
import ru.mirea.ios.store.iosstore.adapter.rest.employee.model.EmployeeRequest;
import ru.mirea.ios.store.iosstore.app.exeption.ConflictException;
import ru.mirea.ios.store.iosstore.app.exeption.NotFoundException;
import ru.mirea.ios.store.iosstore.app.exeption.PrivilegesException;
import ru.mirea.ios.store.iosstore.domain.employee.Employee;

import static ru.mirea.ios.store.iosstore.domain.employee.enums.Position.ADMIN;

@Service
@RequiredArgsConstructor
public class PostEmployeeInbound {
    private final EmployeeRepository employeeRepository;

    public Employee handleRequest(EmployeeRequest body, String adminId) {
        checkData(body.name(), body.lastName(), adminId);

        var employee = Employee.builder()
                .name(body.name())
                .lastname(body.lastName())
                .experience(body.experience())
                .position(body.position())
                .build();

        return employeeRepository.save(employee);
    }

    private void checkData(String name, String lastname, String adminId) {
        var admin = employeeRepository.findById(adminId)
                .orElseThrow(() -> new NotFoundException("Admin with id = " + adminId + " was not found"));
        if (admin.getPosition() != ADMIN) {
            throw new PrivilegesException("User with id = " + adminId + " has no privileges for this move");
        }

        var employee = employeeRepository.findByNameAndLastname(name, lastname);
        if (employee.isPresent()) {
            throw new ConflictException("Employee with this initials already in system");
        }
    }
}
