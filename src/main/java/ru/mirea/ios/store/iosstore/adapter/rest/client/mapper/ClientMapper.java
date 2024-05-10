package ru.mirea.ios.store.iosstore.adapter.rest.client.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mirea.ios.store.iosstore.adapter.rest.client.model.ClientDto;
import ru.mirea.ios.store.iosstore.domain.client.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDto toDto(Client client);
}
