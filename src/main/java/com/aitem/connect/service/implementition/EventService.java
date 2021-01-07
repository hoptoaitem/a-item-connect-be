package com.aitem.connect.service.implementition;

import com.aitem.connect.repository.EventRepository;
import com.aitem.connect.request.EventRequest;
import com.aitem.connect.response.EventResponse;
import com.aitem.connect.model.*;

// import com.aitem.connect.enums.OrderStatus;
// import com.aitem.connect.enums.ProfileType;
// import com.aitem.connect.mapper.AddressMapper;
// import com.aitem.connect.repository.AddressRepository;
// import com.aitem.connect.repository.OrderRepository;
// import com.aitem.connect.repository.RetailerUserRepository;
// import com.aitem.connect.request.AddressRequest;
// import com.aitem.connect.request.ItemRequest;
// import com.aitem.connect.response.OrderResponse;
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
    public EventModel createStore(StoreRequest request, User user) {
        EventModel eventModel = new StoreModel();
        eventModel.setId(UUID.randomUUID().toString());
        eventModel.setEventName(request.getEventName());
        eventModel = storeRepository.save(eventModel);
        return eventModel;
    }

    // public List<StoreResponse> getStores(User user) {

    //     List<StoreModel> storeList = new ArrayList<>();
    //     if (user.getProfileType().equals(ProfileType.RETAILER.name())) {
    //         List<RetailerUserModel> retailerUserModelList
    //                 = retailerUserRepository.findByUserId(user.getId());

    //         for (RetailerUserModel item : retailerUserModelList) {
    //             StoreModel model = storeRepository.findById(item.getStoreId())
    //                     .orElseThrow(IllegalArgumentException::new);
    //             storeList.add(model);
    //         }
    //     } else {
    //         storeList.addAll(storeRepository.findAll());
    //         //storeList.addAll(storeDAO.getStoreForUser(user));
    //     }
    //     return storeList.stream().map(this::getStoreResponse).collect(Collectors.toList());
    // }

    // private StoreResponse getStoreResponse(StoreModel storeModel) {

    //     StoreResponse storeResponse = new StoreResponse();
    //     storeResponse.setId(storeModel.getId());

    //     storeResponse.setAddress(addressRepository.findById(storeModel.getAddressId()).
    //             orElseThrow(IllegalArgumentException::new));
    //     storeResponse.setPhoneNo(storeResponse.getPhoneNo());
    //     storeResponse.setCreatedAt(storeModel.getCreatedAt());
    //     storeResponse.setModifiedAt(storeModel.getModifiedAt());
    //     storeResponse.setCreatedBy(storeModel.getCreatedBy());
    //     storeResponse.setModifiedBy(storeModel.getModifiedBy());
    //     return storeResponse;
    // }

    // public List<OrderResponse> getOrdersByStores(String storeId) {

    //     List<OrderModel> storeOrders = orderRepository.findByStoreId(storeId);

    //     List<OrderResponse> items =
    //             storeOrders.stream().map(model -> {
    //                 OrderResponse orderResponse = new OrderResponse();
    //                 OrderModel orderModel = orderRepository.findById(model.getId())
    //                         .orElseThrow(IllegalArgumentException::new);

    //                 orderResponse.setId(orderModel.getId());
    //                 orderResponse.setOrderExternalReferenceId(orderModel.getOrderExternalReferenceId());
    //                 orderResponse.setOrderStatus(
    //                         OrderStatus.valueOf(orderModel.getOrderStatus()));
    //                 orderResponse.setCreatedAt(orderModel.getCreatedAt());
    //                 orderResponse.setModifiedAt(orderModel.getModifiedAt());
    //                 orderResponse.setCreatedBy(orderModel.getCreatedBy());
    //                 orderResponse.setModifiedBy(orderModel.getModifiedBy());

    //                 return orderResponse;
    //             }).collect(Collectors.toList());

    //     return items;
    // }

    // @Override
    // public List<ItemModel> getItems(String storeId) {
    //     return itemDAO.getItems(storeId);
    // }

    // @Override
    // public ItemModel createItem(ItemRequest request, String storeId) {
    //     return itemDAO.createItem(request, storeId);
    // }
}
