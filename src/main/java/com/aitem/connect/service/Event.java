package com.aitem.connect.service;

import com.aitem.connect.model.EventModel;
import com.aitem.connect.model.User;
import com.aitem.connect.request.EventRequest;
import java.util.List;

public interface Event {
    EventModel createEvent(EventRequest request, User user);
    List<EventModel> getEvents(User user);
}