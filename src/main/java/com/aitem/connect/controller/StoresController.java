package com.aitem.connect.controller;

import com.aitem.connect.model.Authentication;
import com.aitem.connect.model.ItemModel;
import com.aitem.connect.model.StoreModel;
import com.aitem.connect.model.User;
import com.aitem.connect.repository.AuthenticationRepository;
import com.aitem.connect.repository.UserRepository;
import com.aitem.connect.request.ItemRequest;
import com.aitem.connect.request.StoreRequest;
import com.aitem.connect.response.OrderResponse;
import com.aitem.connect.response.StoreResponse;
import com.aitem.connect.service.implementition.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(tags = "Store API")
@CrossOrigin
@RestController
@RequestMapping("/")
public class StoresController {

    private StoreService service;
    private AuthenticationRepository authenticationRepository;
    private UserRepository userRepository;


    private StoresController(
            @Autowired StoreService service,
            @Autowired AuthenticationRepository authenticationRepository,
            @Autowired UserRepository userRepository
    ) {
        this.service = service;
        this.authenticationRepository = authenticationRepository;
        this.userRepository = userRepository;
    }

    @ApiOperation(value = "Get the stores for the user")
    @GetMapping(path = "/stores", consumes = "application/json", produces = "application/json")
    public List<StoreResponse> getStores(@RequestHeader("api-key-token") String key) {

        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId())
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found"));

        return service.getStores(user);
    }

    @ApiOperation(value = "Get the stores for the user")
    @GetMapping(path = "/stores/{store-id}/items", consumes = "application/json", produces = "application/json")
    public List<ItemModel> getStoresItems(
            @RequestHeader("api-key-token") String key,
            @PathVariable("store-id") String storeId) {

        return service.getItems(storeId);
    }

    @PostMapping(path = "/stores/{store-id}/items", consumes = "application/json", produces = "application/json")
    public UUID createStoreItems(@RequestHeader("api-key-token") String key,
                                 @RequestBody ItemRequest request,
                                 @PathVariable("store-id") String storeId) {

        // TODO: move this to common location

        return UUID.fromString(service.createItem(request, storeId).getId());
    }

    @ApiOperation(value = "Get the store orders for the user")
    @GetMapping(path = "/stores/{store-id}/orders", consumes = "application/json", produces = "application/json")
    public List<OrderResponse> getStoresOrders(@RequestHeader("api-key-token") String key,
                                               @PathVariable("store-id") String storeId) {

        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId())
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found"));

        return service.getOrdersByStores(storeId);
    }

    @ApiOperation(value = "Create the stores for the submitted request")
    @PostMapping(path = "/stores", consumes = "application/json", produces = "application/json")
    public UUID createStore(@RequestHeader("api-key-token") String key,
                            @RequestBody StoreRequest request) {

        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId())
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found"));

        StoreModel model = service.createStore(request, user);
        return UUID.fromString(model.getId());
    }
}
