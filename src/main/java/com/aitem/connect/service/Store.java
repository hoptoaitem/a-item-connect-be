package com.aitem.connect.service;

import com.aitem.connect.model.ItemModel;
import com.aitem.connect.model.StoreModel;
import com.aitem.connect.model.User;
import com.aitem.connect.request.ItemRequest;
import com.aitem.connect.request.StoreRequest;
import com.aitem.connect.response.OrderResponse;
import com.aitem.connect.response.StoreResponse;

import java.util.List;

public interface Store {
    StoreModel createStore(StoreRequest request, User user);

    List<StoreResponse> getStores(User user);

    List<OrderResponse> getOrdersByStores(String storeId);

    List<ItemModel> getItems(String storeId);

    ItemModel createItem(ItemRequest request, String storeId );

    List<StoreResponse> getEventStores(User user, String eventId);

    StoreModel createEventStore(StoreRequest request, String eventId, User user);
}