package com.aitem.connect.controller;

import com.aitem.connect.model.Authentication;
import com.aitem.connect.model.ItemModel;
import com.aitem.connect.model.User;
import com.aitem.connect.repository.AuthenticationRepository;
import com.aitem.connect.repository.UserRepository;
import com.aitem.connect.request.ItemRequest;
import com.aitem.connect.service.implementition.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(tags = "Item API")
@CrossOrigin
@RestController
@RequestMapping("/")
public class ItemController {


    private ItemService service;
    private UserRepository userRepository;
    private AuthenticationRepository authenticationRepository;


    private ItemController(
            @Autowired ItemService service,
            @Autowired UserRepository userRepository,
            @Autowired AuthenticationRepository authenticationRepository
    ) {
        this.service = service;
        this.authenticationRepository = authenticationRepository;
        this.userRepository = userRepository;
    }

    /*
    @ApiOperation(value = "get the items for the store")
    @GetMapping(path = "/items", consumes = "application/json", produces = "application/json")
    public List<ItemModel> getItems(@RequestHeader("api-key-token") String key,
                                    @RequestParam("store_id") String storeId) {
        // TODO : validate user type
        return service.getItems(storeId);
    }*/

    @ApiOperation(value = "get the items for the store")
    @GetMapping(path = "/items", consumes = "application/json", produces = "application/json")
    public List<ItemModel> getItems(@RequestHeader("api-key-token") String key) {
        // TODO : validate user type

        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId())
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found"));

        return service.getItems(user);
    }

    // TODO : pass as list
    @PostMapping(path = "/items", consumes = "application/json", produces = "application/json")
    public UUID createItems(@RequestHeader("api-key-token") String key,
                            @RequestBody ItemRequest request) {

        // TODO: move this to common location
        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId())
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found"));

        ItemModel model = service.createItem(request, user);
        return UUID.fromString(model.getId());
    }
}
