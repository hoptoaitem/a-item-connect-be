package com.aitem.connect.service;

import com.aitem.connect.model.ItemModel;
import com.aitem.connect.model.User;
import com.aitem.connect.request.ItemRequest;

import java.util.List;

public interface Item {

    ItemModel createItem(ItemRequest request, User user);

    List<ItemModel> getItems(String storeId);
}
