package com.aitem.connect.controller;

import com.aitem.connect.model.Authentication;
import com.aitem.connect.model.EventModel;
import com.aitem.connect.model.User;
import com.aitem.connect.repository.UserRepository;
import com.aitem.connect.request.EventRequest;
import com.aitem.connect.request.StartEventRequest;
import com.aitem.connect.repository.AuthenticationRepository;
import com.aitem.connect.service.implementition.EventService;
import com.aitem.connect.response.EventProductsResponse;
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
    public UUID createEvent(@RequestHeader("api-key-token") String key, @RequestBody EventRequest request) {
        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        EventModel model = service.createEvent(request, user);
        return UUID.fromString(model.getId());
    }

    @ApiOperation(value = "Get the events for the user")
    @GetMapping(path = "/events", consumes = "application/json", produces = "application/json")
    public List<EventModel> getEvents(@RequestHeader("api-key-token") String key) {
        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return service.getEvents(user);
    }

    @ApiOperation(value = "Delete event for the user")
    @DeleteMapping(path = "/event/{event-id}", consumes = "application/json", produces = "application/json")
    public Integer getStoresOrders(@RequestHeader("api-key-token") String key, @PathVariable("event-id") String eventId) {
        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return service.deleteEvent(user, eventId);
    }

    @ApiOperation(value = "Start event for the user")
    @PutMapping(path = "/event/{event-id}/start", consumes = "application/json", produces = "application/json")
    public EventModel startEvent(@RequestHeader("api-key-token") String key, @PathVariable("event-id") String eventId, @RequestBody StartEventRequest request) {
        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return service.updateCart(user, eventId, 1, request.getStopAt(), request.getCount());
    }

    @ApiOperation(value = "Stop event for the user")
    @PutMapping(path = "/event/{event-id}/stop", consumes = "application/json", produces = "application/json")
    public EventModel stopEvent(@RequestHeader("api-key-token") String key, @PathVariable("event-id") String eventId) {
        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return service.updateCart(user, eventId, 2, "", 0);
    }

    @ApiOperation(value = "Get the events for the user")
    @GetMapping(path = "/events/products", consumes = "application/json", produces = "application/json")
    public List<EventProductsResponse> getAvailableEventProducts(@RequestHeader("api-key-token") String key) {
        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return service.getAvailableEventProducts(user);
    }
}
