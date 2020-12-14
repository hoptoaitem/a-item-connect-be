package com.aitem.connect.service.implementition;

import com.aitem.connect.enums.OrderStatus;
import com.aitem.connect.model.OrderModel;
import com.aitem.connect.model.User;
import com.aitem.connect.repository.OrderRepository;
import com.aitem.connect.repository.UserRepository;
import com.aitem.connect.request.UpdateOrderRequest;
import com.aitem.connect.utils.NotificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderDriverDAO {

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private NotificationUtils notificationUtils;

    private OrderDriverDAO(
            @Autowired OrderRepository orderRepository,
            @Autowired UserRepository userRepository,
            @Autowired NotificationUtils notificationUtils
    ) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.notificationUtils = notificationUtils;
    }

    public OrderModel assignOrderToDriver(UpdateOrderRequest request) {

        // TODO: profile type and ph unique contraint
        User user = userRepository.findByPhone(request.getDriverPhone());

        OrderModel orderModel = orderRepository.findById(request.getOrderId())
                .orElseThrow(IllegalArgumentException::new);
        orderModel.setDriverId(user.getId());
        orderModel.setOrderStatus(OrderStatus.WAITING_ACCEPTANCE_FROM_DRIVER.name());
        orderRepository.save(orderModel);
        notificationUtils.sendNotification(user);

        return orderModel;
    }
}
