package com.aitem.connect.service;

import com.aitem.connect.model.ItemModel;
import com.aitem.connect.model.OrderModel;
import com.aitem.connect.model.User;
import com.aitem.connect.request.CartRequest;
import com.aitem.connect.request.OrderRequest;
import com.aitem.connect.response.CartResponse;
import com.aitem.connect.response.OrderResponse;

import java.util.List;

public interface Cart {

    public CartResponse updateCart(CartRequest request, User user);
    public CartResponse getCart(User user);
}
