package com.aitem.connect.service;

import com.aitem.connect.model.ItemModel;
import com.aitem.connect.model.OrderModel;
import com.aitem.connect.request.ItemRequest;
import com.aitem.connect.request.OrderRequest;

import java.util.List;

public interface Item {

    ItemModel createItem(ItemRequest request);

    List<ItemModel> getItems(String storeId);
}
