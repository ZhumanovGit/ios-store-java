package ru.mirea.ios.store.iosstore.adapter.rest.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.ios.store.iosstore.adapter.rest.order.mapper.OrderMapper;
import ru.mirea.ios.store.iosstore.adapter.rest.order.model.NewOrderRequest;
import ru.mirea.ios.store.iosstore.adapter.rest.order.model.OrderDto;
import ru.mirea.ios.store.iosstore.app.inbound.order.GetAllOrdersByClientInbound;
import ru.mirea.ios.store.iosstore.app.inbound.order.GetBestOrdersForLastMonthInbound;
import ru.mirea.ios.store.iosstore.app.inbound.order.PostOrderInbound;
import ru.mirea.ios.store.iosstore.app.inbound.order.UpdateOrderStatusInbound;
import ru.mirea.ios.store.iosstore.domain.order.enums.Status;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/orders")
public class OrderController {
    private final GetAllOrdersByClientInbound getAllOrdersByClientInbound;
    private final PostOrderInbound postOrderInbound;
    private final UpdateOrderStatusInbound updateOrderStatusInbound;
    private final GetBestOrdersForLastMonthInbound getBestOrdersForLastMonthInbound;
    private final OrderMapper orderMapper;

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrdersByClient(@RequestParam String userId) {
        var orders = getAllOrdersByClientInbound.handleRequest(userId);
        var dtos = orders.stream()
                .map(orderMapper::toDto)
                .toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> postOrder(@RequestBody NewOrderRequest body,
                                            @RequestParam String employeeId) {
        var order = postOrderInbound.handleRequest(employeeId, body);
        return new ResponseEntity<>("Created order with id = " + order.getId(), HttpStatus.OK);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable String orderId,
                                                      @RequestParam String employeeId,
                                                      @RequestParam Status status) {
        var order = updateOrderStatusInbound.handleRequest(orderId, employeeId, status);
        return new ResponseEntity<>("Order with id = " + orderId + " status was updated, new status = " + order.getStatus()
                ,HttpStatus.OK);
    }

    @GetMapping("/last-month")
    public ResponseEntity<List<OrderDto>> getOrdersForLastMonth(@RequestParam String userId,
                                                                @RequestParam(required = false, defaultValue = "0.0") Double amount,
                                                                @RequestParam(required = false, defaultValue = "0") Integer productsCount) {
        var orders = getBestOrdersForLastMonthInbound.handleRequest(userId, amount, productsCount);
        var dtos = orders.stream()
                .map(orderMapper::toDto)
                .toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}
