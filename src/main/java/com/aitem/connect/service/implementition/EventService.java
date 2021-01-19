package com.aitem.connect.service.implementition;

import com.aitem.connect.repository.*;
import com.aitem.connect.request.EventRequest;
import com.aitem.connect.response.EventResponse;
import com.aitem.connect.response.EventProductsResponse;
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
    private RetailerUserRepository retailerUserRepository;
    private StoreRepository storeRepository;
    private ItemDAO itemDAO;

    private EventService(
        @Autowired EventRepository eventRepository,
        @Autowired ItemDAO itemDAO,
        @Autowired RetailerUserRepository retailerUserRepository,
        @Autowired StoreRepository storeRepository
    ) {
        this.eventRepository = eventRepository;
        this.itemDAO = itemDAO;
        this.retailerUserRepository = retailerUserRepository;
        this.storeRepository = storeRepository;
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
                    SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
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

    public List<EventProductsResponse> getAvailableEventProducts(User user) {
        if(user.getProfileType().equals(ProfileType.SHOPPER.name())) {
            List<EventModel> events = eventRepository.findByStatus(new Long(1));
            List<EventProductsResponse> response = new ArrayList<EventProductsResponse>();
            Date nowDate = new Date();

            for (EventModel event : events) {
                if(nowDate.before(event.getStopAt())) {
                    response.addAll(getEventProductsResponseWithEvent(event));
                }
            }

            return response;
        } else {
            return null;
        }
    }

    private List<EventProductsResponse> getEventProductsResponseWithEvent(EventModel eventModel) {
        List<RetailerUserModel> retailerUserModelList = retailerUserRepository.findByUserId(eventModel.getId());
        List<EventProductsResponse> result = new ArrayList<EventProductsResponse>();

        for (RetailerUserModel item : retailerUserModelList) {
            StoreModel model = storeRepository.findById(item.getStoreId()).orElseThrow(IllegalArgumentException::new);
            if(model.getType() == 1) {
                result.addAll(getEventProductsResponseWithStore(eventModel, model));
            }
        }

        return result;
    }

    private List<EventProductsResponse> getEventProductsResponseWithStore(EventModel eventModel, StoreModel storeModel) {
        List<ItemModel> items = itemDAO.getItems(storeModel.getId());
        List<EventProductsResponse> results = new ArrayList<EventProductsResponse>();

        for(ItemModel item : items) {
            EventProductsResponse result = new EventProductsResponse();
            result.setId(item.getId());
            result.setName(item.getName());
            result.setType(item.getType());
            result.setPrice(item.getPrice());
            result.setQuantity(item.getQuantity());
            result.setWebsite(item.getWebsite());
            result.setDescription(item.getDescription());
            result.setShortDescription(item.getShortDescription());
            result.setSku(item.getSku());
            result.setWeight(item.getWeight());
            result.setPictureId(item.getPictureId());
            result.setEndDate(eventModel.getStopAt());
            AddressModel addressModel = addressRepository.findById(storeModel.getAddressId()).orElseThrow(() -> new IllegalArgumentException("Address not found"));
            result.setStoreName(addressModel.getAddressName());
            result.setStreetAddress(addressModel.getStreetAddress());
            result.setStreetAddress1(addressModel.getStreetAddress1());
            result.setCity(addressModel.getCity());
            result.setState(addressModel.getState());
            result.setZip(addressModel.getZip());
            result.setEventName(eventModel.getName());
            results.add(result);
        }

        return results;
    }
}
