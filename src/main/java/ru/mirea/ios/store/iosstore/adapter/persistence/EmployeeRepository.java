package ru.mirea.ios.store.iosstore.adapter.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.mirea.ios.store.iosstore.domain.employee.Employee;

import java.util.Optional;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

    Optional<Employee> findByNameAndLastname(String name, String lastname);
}
