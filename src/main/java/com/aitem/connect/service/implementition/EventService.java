package com.aitem.connect.service.implementition;

import com.aitem.connect.repository.EventRepository;
import com.aitem.connect.request.EventRequest;
import com.aitem.connect.response.EventResponse;
import com.aitem.connect.model.*;
import com.aitem.connect.enums.ProfileType;
import com.aitem.connect.service.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventService implements Event {
    private EventRepository eventRepository;
    // private OrderRepository orderRepository;
    // private RetailerUserRepository retailerUserRepository;
    // private ItemDAO itemDAO;
    // private StoreDAO storeDAO;

    private EventService(
        @Autowired EventRepository eventRepository
    ) {
        this.eventRepository = eventRepository;
        // this.addressRepository = addressRepository;
        // this.retailerUserRepository = retailerUserRepository;
        // this.orderRepository = orderRepository;
        // this.itemDAO = itemDAO;
        // this.storeDAO = storeDAO;
    }

    @Override
    public EventModel createEvent(EventRequest request, User user) {
        EventModel eventModel = new EventModel();
        eventModel.setId(UUID.randomUUID().toString());
        eventModel.setName(request.getEventName());
        eventModel.setCreatedBy(user.getId());
        eventModel.setModifiedBy(user.getId());
        eventModel = eventRepository.save(eventModel);
        return eventModel;
    }

    public List<EventModel> getEvents(User user) {
        if (user.getProfileType().equals(ProfileType.ADMIN.name())) {
            //return eventRepository.findByCreatorId(user.getId());
            return eventRepository.findAll();
        } else {
            return null;
        }
    }
}
