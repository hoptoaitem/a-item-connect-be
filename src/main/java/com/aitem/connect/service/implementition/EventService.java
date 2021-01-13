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
import java.text.SimpleDateFormat;

@Service
public class EventService implements Event {
    private EventRepository eventRepository;

    private EventService(
        @Autowired EventRepository eventRepository
    ) {
        this.eventRepository = eventRepository;
    }

    @Override
    public EventModel createEvent(EventRequest request, User user) {
        EventModel eventModel = new EventModel();
        eventModel.setId(UUID.randomUUID().toString());
        eventModel.setName(request.getEventName());
        eventModel.setCreatedBy(user.getId());
        eventModel.setModifiedBy(user.getId());
        eventModel.setCreatedAt(new Date());
        eventModel.setModifiedAt(new Date());
        eventModel = eventRepository.save(eventModel);
        return eventModel;
    }

    public List<EventModel> getEvents(User user) {
        if (user.getProfileType().equals(ProfileType.ADMIN.name())) {
            List<EventModel> events = eventRepository.findByCreatedBy(user.getId());
            Date nowDate = new Date();

            for (EventModel event : events) {
                if(event.getStatus() == 1 && nowDate.after(event.getStopAt())) {
                    event.setStatus(2);
                    eventRepository.save(event);
                }
            }

            return events;
        } else if(user.getProfileType().equals(ProfileType.SHOPPER.name())) {
            List<EventModel> events = eventRepository.findByStatus(new Long(1));
            List<EventModel> results = new ArrayList();
            Date nowDate = new Date();

            for (EventModel event : events) {
                if(nowDate.before(event.getStopAt())) {
                    results.add(event);
                }
            }
            
            return results;
        } else {
            return null;
        }
    }

    public Integer deleteEvent(User user, String eventId) {
        if (user.getProfileType().equals(ProfileType.ADMIN.name())) {
            try {
                eventRepository.deleteById(eventId);
                return 1;
            } catch(Exception e) {
                return -1;
            }
        } else {
            return -1;
        }
    }

    public EventModel updateCart(User user, String eventId, Integer status, String stopAt) {
        if (user.getProfileType().equals(ProfileType.ADMIN.name())) {
            EventModel event = eventRepository.findById(eventId).orElseThrow(IllegalArgumentException::new);
            event.setStatus(status);

            if(status == 1) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy h:mm a");
                    Date date = format.parse(stopAt);
                    event.setStopAt(date);
                } catch(Exception e) {
                    return null;
                }
            }

            event = eventRepository.save(event);
            return event;
        } else {
            return null;
        }
    }
}
