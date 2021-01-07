package com.aitem.connect.service;

import com.aitem.connect.model.EventModel;
import com.aitem.connect.model.User;
import com.aitem.connect.request.EventRequest;

// import com.aitem.connect.model.ItemModel;
// import com.aitem.connect.request.ItemRequest;
// import com.aitem.connect.response.OrderResponse;
// import com.aitem.connect.response.StoreResponse;

import java.util.List;

public interface Event {

    EventModel createEvent(EventRequest request, User user);

    // List<StoreResponse> getStores(User user);

    // List<OrderResponse> getOrdersByStores(String storeId);

    // List<ItemModel> getItems(String storeId);

    // ItemModel createItem(ItemRequest request, String storeId );
}