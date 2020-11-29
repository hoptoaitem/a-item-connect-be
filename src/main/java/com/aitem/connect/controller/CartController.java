package com.aitem.connect.controller;

import com.aitem.connect.model.Authentication;
import com.aitem.connect.model.User;
import com.aitem.connect.repository.AuthenticationRepository;
import com.aitem.connect.repository.UserRepository;
import com.aitem.connect.request.CartRequest;
import com.aitem.connect.response.CartResponse;
import com.aitem.connect.service.implementition.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Cart API")
@CrossOrigin
@RestController
@RequestMapping("/")
public class CartController {


    private CartService service;
    private UserRepository userRepository;
    private AuthenticationRepository authenticationRepository;


    private CartController(
            @Autowired CartService service,
            @Autowired UserRepository userRepository,
            @Autowired AuthenticationRepository authenticationRepository
    ) {
        this.service = service;
        this.authenticationRepository = authenticationRepository;
        this.userRepository = userRepository;
    }

    @ApiOperation(value = "update the cart items for the customer")
    @PutMapping(path = "/carts", consumes = "application/json", produces = "application/json")
    public CartResponse updateCart(@RequestHeader("api-key-token") String key,
                                    @RequestBody CartRequest request) {
        // TODO : validate user type
        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId())
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found"));

        return service.updateCart(request,user);
    }

    @ApiOperation(value = "get the items for the store")
    @GetMapping(path = "/carts", consumes = "application/json", produces = "application/json")
    public CartResponse getCart(@RequestHeader("api-key-token") String key) {

        // TODO: move this to common location
        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId())
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found"));

        return service.getCart(user);
    }
}
