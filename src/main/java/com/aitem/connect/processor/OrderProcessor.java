package com.aitem.connect.processor;

import com.aitem.connect.enums.OrderStatus;
import com.aitem.connect.model.*;
import com.aitem.connect.repository.*;
import com.aitem.connect.request.UpdateOrderRequest;
import com.aitem.connect.service.implementition.OrderDAO;
import com.aitem.connect.service.implementition.OrderDriverDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderProcessor {


    private OrderRepository orderRepository;
    private AddressRepository addressRepository;
    private UserRepository userRepository;
    private StoreRepository storeRepository;
    private OrderDriverDAO orderDriverDAO;
    private ZipDriverRepository zipDriverRepository;

    public OrderProcessor(
            @Autowired OrderRepository orderRepository,
            @Autowired AddressRepository addressRepository,
            @Autowired UserRepository userRepository,
            @Autowired StoreRepository storeRepository,
            @Autowired ZipDriverRepository zipDriverRepository,
            @Autowired OrderDriverDAO orderDriverDAO
    ) {
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
        this.orderDriverDAO = orderDriverDAO;
        this.zipDriverRepository = zipDriverRepository;
    }

    @Async("threadPoolTaskExecutor")
    public void handleDriverForOrder(OrderModel orderModel) {
        StoreModel storeModel = storeRepository.findById(orderModel.getStoreId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Store not found"));

        AddressModel addressModel = addressRepository.findById(storeModel.getAddressId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Address not found"));

        List<ZipDriver> driversInZip = zipDriverRepository
                .findByZip(addressModel.getZip());

        if (driversInZip != null && !driversInZip.isEmpty()) {
            driversInZip.sort(
                    Comparator.comparing(u -> Integer.valueOf(u.getDistance()))
            );
            driversInZip = driversInZip
                    .stream()
                    .limit(10)
                    .collect(Collectors.toList());

            for (ZipDriver zipDriver : driversInZip) {

                String driverId = zipDriver.getDriverId();
                User user = userRepository.findById(driverId)
                        .orElseThrow(
                                () -> new IllegalArgumentException("User not found"));

                UpdateOrderRequest request = new UpdateOrderRequest();
                request.setDriverPhone(user.getPhone());
                request.setOrderId(orderModel.getId());
                orderDriverDAO.assignOrderToDriver(request);
                try {
                    Thread.sleep(1000 * 120);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                OrderModel orderModelLatest = orderRepository.findById(orderModel.getId())
                        .orElseThrow(
                                () -> new IllegalArgumentException("Order not found"));

                if (orderModelLatest.getOrderStatus().equals(OrderStatus.DRIVER_ACCEPTED.name())) {
                    break;
                }
            }
        }
    }
}
