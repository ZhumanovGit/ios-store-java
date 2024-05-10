package ru.mirea.ios.store.iosstore.adapter.rest.order.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mirea.ios.store.iosstore.adapter.rest.order.model.OrderDto;
import ru.mirea.ios.store.iosstore.domain.order.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "customer.name", target = "clientName")
    @Mapping(source = "seller.name", target = "employeeName")
    OrderDto toDto(Order order);
}
