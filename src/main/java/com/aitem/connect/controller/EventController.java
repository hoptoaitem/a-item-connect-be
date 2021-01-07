package com.aitem.connect.controller;

import com.aitem.connect.model.Authentication;
import com.aitem.connect.model.EventModel;
import com.aitem.connect.model.User;
import com.aitem.connect.repository.UserRepository;
import com.aitem.connect.request.EventRequest;
import com.aitem.connect.repository.AuthenticationRepository;
import com.aitem.connect.service.implementition.EventService;
import com.aitem.connect.response.EventResponse;

// import com.aitem.connect.model.ItemModel;
// import com.aitem.connect.request.ItemRequest;
// import com.aitem.connect.response.OrderResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(tags = "Event API")
@CrossOrigin
@RestController
@RequestMapping("/")
public class EventController {
    private EventService service;
    private AuthenticationRepository authenticationRepository;
    private UserRepository userRepository;

    private EventController(
            @Autowired EventService service,
            @Autowired AuthenticationRepository authenticationRepository,
            @Autowired UserRepository userRepository
    ) {
        this.service = service;
        this.authenticationRepository = authenticationRepository;
        this.userRepository = userRepository;
    }

    @ApiOperation(value = "Create the event for the submitted request")
    @PostMapping(path = "/event", consumes = "application/json", produces = "application/json")
    public UUID createEvent(@RequestHeader("api-key-token") String key,
                            @RequestBody EventRequest request) {
        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        EventModel model = service.createEvent(request, user);
        return UUID.fromString(model.getId());
    }

    @ApiOperation(value = "Get the events for the user")
    @GetMapping(path = "/events", consumes = "application/json", produces = "application/json")
    public List<EventResponse> getEvents(@RequestHeader("api-key-token") String key) {
        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return service.getEvents(user);
    }
}
