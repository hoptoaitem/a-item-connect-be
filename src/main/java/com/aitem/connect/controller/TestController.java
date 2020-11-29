package com.aitem.connect.controller;

import com.aitem.connect.model.Authentication;
import com.aitem.connect.model.User;
import com.aitem.connect.repository.AuthenticationRepository;
import com.aitem.connect.repository.UserRepository;
import com.aitem.connect.service.implementition.ItemService;
import com.aitem.connect.utils.NotificationUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api(tags = "Item")
@CrossOrigin
@RestController
@RequestMapping("/")
public class TestController {


    private ItemService service;
    private UserRepository userRepository;
    private AuthenticationRepository authenticationRepository;
    private NotificationUtils notificationUtils;


    private TestController(
            @Autowired ItemService service,
            @Autowired UserRepository userRepository,
            @Autowired AuthenticationRepository authenticationRepository,
            @Autowired NotificationUtils notificationUtils
    ) {
        this.service = service;
        this.authenticationRepository = authenticationRepository;
        this.userRepository = userRepository;
        this.notificationUtils = notificationUtils;
    }


    // TODO : pass as list
    @GetMapping(path = "/test", consumes = "application/json", produces = "application/json")
    public UUID testNotification(@RequestHeader("api-key-token") String key) {

        // TODO: move this to common location
        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId())
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found"));

        notificationUtils.sendNotification(user);

        return UUID.fromString(user.getId());
    }
}
