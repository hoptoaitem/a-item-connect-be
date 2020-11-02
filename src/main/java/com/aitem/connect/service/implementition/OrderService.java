package com.aitem.connect.service.implementition;

import com.aitem.connect.enums.OrderStatus;
import com.aitem.connect.enums.ProfileType;
import com.aitem.connect.model.*;
import com.aitem.connect.repository.*;
import com.aitem.connect.request.OrderRequest;
import com.aitem.connect.request.UpdateOrderRequest;
import com.aitem.connect.response.ItemResponse;
import com.aitem.connect.response.OrderResponse;
import com.aitem.connect.service.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService implements Order {


    private OrderDAO orderDAO;


    private OrderService(
            @Autowired OrderDAO orderDAO
    ) {
        this.orderDAO = orderDAO;
    }

    @Override
    public OrderModel createOrder(OrderRequest request, User user) {
        return orderDAO.createOrder(request, user);
    }

    @Override
    public List<OrderResponse> getOrder(User user) {
        return orderDAO.getOrder(user);
    }

    @Override
    public List<OrderResponse> getOrderHistory(User user) {
        return orderDAO.getOrderHistory(user);
    }

    @Override
    public OrderModel updateOrder(UpdateOrderRequest request, User user) {
        return orderDAO.updateOrderStatus(request, user);
    }
}
