package com.aitem.connect.service.implementition;

import com.aitem.connect.model.OrderModel;
import com.aitem.connect.model.User;
import com.aitem.connect.request.OrderRequest;
import com.aitem.connect.request.UpdateOrderRequest;
import com.aitem.connect.response.OrderResponse;
import com.aitem.connect.service.Order;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if(StringUtils.isNotEmpty(request.getDriverPhone())){
            return assignOrderToDriver(request);
        }
        return orderDAO.updateOrderStatus(request, user);
    }

    private OrderModel assignOrderToDriver(UpdateOrderRequest request) {

        return orderDAO.assignOrderToDriver(request);
    }
}
