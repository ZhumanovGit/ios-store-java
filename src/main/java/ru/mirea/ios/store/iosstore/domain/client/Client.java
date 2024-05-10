package ru.mirea.ios.store.iosstore.domain.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.mirea.ios.store.iosstore.domain.employee.enums.Position;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "clients")
public class Client {
    @Id
    private String id;
    private String name;
    private String lastname;
    private String login;
    @JsonIgnore
    private String password;
    private Position position;

    Position getPosition() {
        return Position.USER;
    }
}
