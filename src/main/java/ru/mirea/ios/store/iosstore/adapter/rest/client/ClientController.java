package ru.mirea.ios.store.iosstore.adapter.rest.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.ios.store.iosstore.adapter.rest.client.mapper.ClientMapper;
import ru.mirea.ios.store.iosstore.adapter.rest.client.model.ClientDto;
import ru.mirea.ios.store.iosstore.adapter.rest.client.model.NewClientRequest;
import ru.mirea.ios.store.iosstore.app.inbound.client.GetActiveClientByPeriodInbound;
import ru.mirea.ios.store.iosstore.app.inbound.client.PostUserInbound;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/clients")
public class ClientController {
    private final GetActiveClientByPeriodInbound getActiveClientByPeriodInbound;
    private final PostUserInbound postUserInbound;
    private final ClientMapper mapper;

    @GetMapping("/most-active")
    public ResponseEntity<List<ClientDto>> getActiveClientsByPeriodInbound(@RequestParam Long orderCount,
                                                                           @RequestParam String userId) {
        var clients = getActiveClientByPeriodInbound.handleRequest(userId, orderCount);
        var dtos = clients.stream()
                .map(mapper::toDto)
                .toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> postClient(@RequestBody NewClientRequest body) {
        var client = postUserInbound.handleRequest(body);
        return new ResponseEntity<>("Created user with id = " + client, HttpStatus.OK);
    }
}
