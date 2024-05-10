package ru.mirea.ios.store.iosstore.domain.employee;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.mirea.ios.store.iosstore.domain.employee.enums.Position;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "employees")
public class Employee {
    @Id
    private String id;
    private String name;
    private String lastname;
    private int experience;
    private Position position;
}
