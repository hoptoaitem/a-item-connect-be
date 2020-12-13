package com.aitem.connect.processor;

import com.aitem.connect.enums.OrderStatus;
import com.aitem.connect.model.*;
import com.aitem.connect.repository.*;
import com.aitem.connect.request.ItemRequest;
import com.aitem.connect.request.UpdateOrderRequest;
import com.aitem.connect.service.implementition.OrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderProcessor {


    private CartRepository cartRepository;
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;
    private AddressRepository addressRepository;
    private UserRepository userRepository;
    private ItemRepository itemRepository;
    private StoreRepository storeRepository;
    private RetailerUserRepository retailerUserRepository;
    private OrderDAO orderDAO;
    private ZipDriverRepository zipDriverRepository;

    private OrderProcessor(
            @Autowired CartRepository cartRepository,
            @Autowired OrderRepository orderRepository,
            @Autowired OrderDetailRepository orderDetailRepository,
            @Autowired AddressRepository addressRepository,
            @Autowired UserRepository userRepository,
            @Autowired ItemRepository itemRepository,
            @Autowired StoreRepository storeRepository,
            @Autowired RetailerUserRepository retailerUserRepository,
            @Autowired ZipDriverRepository zipDriverRepository,
            @Autowired OrderDAO orderDAO
    ) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.storeRepository = storeRepository;
        this.retailerUserRepository = retailerUserRepository;
        this.cartRepository = cartRepository;
        this.orderDAO = orderDAO;
        this.zipDriverRepository = zipDriverRepository;
        this.orderDAO = orderDAO;
    }

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
                orderDAO.assignOrderToDriver(request);
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
