package ru.mirea.ios.store.iosstore.adapter.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.mirea.ios.store.iosstore.domain.client.Client;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ClientRepository extends MongoRepository<Client, String> {
    List<Client> findAllByIdIn(Set<String> ids);

    Optional<Client> findClientByLogin(String login);
}
