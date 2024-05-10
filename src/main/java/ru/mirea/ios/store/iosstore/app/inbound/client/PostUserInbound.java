package ru.mirea.ios.store.iosstore.app.inbound.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.ios.store.iosstore.adapter.persistence.ClientRepository;
import ru.mirea.ios.store.iosstore.adapter.rest.client.model.NewClientRequest;
import ru.mirea.ios.store.iosstore.app.exeption.ConflictException;
import ru.mirea.ios.store.iosstore.domain.client.Client;

import static ru.mirea.ios.store.iosstore.domain.employee.enums.Position.USER;

@Service
@RequiredArgsConstructor
public class PostUserInbound {
    private final ClientRepository clientRepository;

    public Client handleRequest(NewClientRequest body) {
        checkLogin(body.login());

        var client = Client.builder()
                .name(body.name())
                .lastname(body.lastname())
                .login(body.login())
                .password(body.password())
                .position(USER)
                .build();
        return clientRepository.save(client);
    }

    private void checkLogin(String login) {
        var client = clientRepository.findClientByLogin(login);
        if (client.isPresent()) {
            throw new ConflictException("Login: " + login + " already taken");
        }
    }
}
