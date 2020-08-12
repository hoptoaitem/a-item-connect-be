package com.aitem.connect.service;

import com.aitem.connect.model.OrderModel;
import com.aitem.connect.model.User;
import com.aitem.connect.request.OrderRequest;
import com.aitem.connect.request.UpdateOrderRequest;
import com.aitem.connect.response.OrderResponse;

import java.util.List;

public interface Order {

    OrderModel createOrder(OrderRequest request, User user);

    List<OrderResponse> getOrder(User user);

    OrderModel updateOrder(UpdateOrderRequest request, User user);
}
