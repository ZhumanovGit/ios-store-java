package ru.mirea.ios.store.iosstore.adapter.rest.employee.model;

import ru.mirea.ios.store.iosstore.domain.employee.enums.Position;

public record EmployeeRequest(String name, String lastName, Position position, Integer experience) {
}
