package com.aitem.connect.service;

import com.aitem.connect.model.EventModel;
import com.aitem.connect.model.User;
import com.aitem.connect.request.EventRequest;
import com.aitem.connect.response.EventProductsResponse;
import java.util.List;

public interface Event {
    EventModel createEvent(EventRequest request, User user);
    List<EventModel> getEvents(User user);
    Integer deleteEvent(User user, String eventId);
    EventModel updateCart(User user, String eventId, Integer status, String stopAt, Integer count);
    List<EventProductsResponse> getAvailableEventProducts(User user);
}