package com.aitem.connect.controller;

import com.aitem.connect.model.Authentication;
import com.aitem.connect.model.OrderModel;
import com.aitem.connect.model.User;
import com.aitem.connect.repository.AuthenticationRepository;
import com.aitem.connect.repository.UserRepository;
import com.aitem.connect.request.OrderRequest;
import com.aitem.connect.request.UpdateOrderRequest;
import com.aitem.connect.response.OrderResponse;
import com.aitem.connect.service.implementition.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(tags = "Order API")
@CrossOrigin
@RestController
@RequestMapping("/")
public class OrderController {
    private OrderService service;
    private AuthenticationRepository authenticationRepository;
    private UserRepository userRepository;


    private OrderController(
            @Autowired OrderService service,
            @Autowired AuthenticationRepository authenticationRepository,
            @Autowired UserRepository userRepository) {
        this.service = service;
        this.authenticationRepository = authenticationRepository;
        this.userRepository = userRepository;
    }

    @ApiOperation(value = "Get the orders for the store")
    @GetMapping(path = "/orders", consumes = "application/json", produces = "application/json")
    public List<OrderResponse> getOrders(@RequestHeader("api-key-token") String key) {
        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId())
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found"));

        return service.getOrder(user);
    }

    @ApiOperation(value = "Get the orders history")
    @GetMapping(path = "/orders/history", consumes = "application/json", produces = "application/json")
    public List<OrderResponse> getOrdersHistory(@RequestHeader("api-key-token") String key) {

        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId())
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found"));

        return service.getOrderHistory(user);
    }

    @ApiOperation(value = "Created the orders for the store")
    @PostMapping(path = "/orders", consumes = "application/json", produces = "application/json")
    public UUID createOrder(@RequestHeader("api-key-token") String key,
                            @RequestBody OrderRequest request) {

        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId())
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found"));

        OrderModel model = service.createOrder(request, user);
        return UUID.fromString(model.getId());
    }

    @ApiOperation(value = "Update orders for the store")
    @PutMapping(path = "/orders/{order_id}", consumes = "application/json", produces = "application/json")
    public UUID updateOrder(@RequestHeader("api-key-token") String key,
                            @PathVariable("order_id") String orderId,
                            @RequestBody UpdateOrderRequest request) {

        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId())
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found"));

        // TODO: match orderID and request orderID
        OrderModel model = service.updateOrder(request, user);
        return UUID.fromString(model.getId());
    }

    @ApiOperation(value = "Assign orders to the driver")
    @PutMapping(path = "/orders/{order_id}/driver", consumes = "application/json", produces = "application/json")
    public UUID updateOrderToDriver(@RequestHeader("api-key-token") String key,
                                    @PathVariable("order_id") String orderId,
                                    @RequestBody UpdateOrderRequest request) {

        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId())
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found"));

        // TODO: match orderID and request orderID
        OrderModel model = service.updateOrder(request, user);
        return UUID.fromString(model.getId());
    }
}
