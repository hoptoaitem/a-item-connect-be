package com.aitem.connect.service.implementition;

import com.aitem.connect.enums.OrderStatus;
import com.aitem.connect.enums.ProfileType;
import com.aitem.connect.model.*;
import com.aitem.connect.repository.*;
import com.aitem.connect.request.OrderRequest;
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

    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;
    private AddressRepository addressRepository;
    private UserRepository userRepository;
    private ItemRepository itemRepository;
    private StoreRepository storeRepository;
    private RetailerUserRepository retailerUserRepository;


    private OrderService(
            @Autowired OrderRepository orderRepository,
            @Autowired OrderDetailRepository orderDetailRepository,
            @Autowired AddressRepository addressRepository,
            @Autowired UserRepository userRepository,
            @Autowired ItemRepository itemRepository,
            @Autowired StoreRepository storeRepository,
            @Autowired RetailerUserRepository retailerUserRepository


    ) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.storeRepository = storeRepository;
        this.retailerUserRepository = retailerUserRepository;
    }

    @Override
    public OrderModel createOrder(OrderRequest request, User user) {
        /*
        private String id;
        @Column(name="order_external_ref_id")
        private String orderExternalReferenceId;
        private Date purchasedOn;
        @Column(name="status")
        private OrderStatus orderStatus;
        */
        OrderModel model = new OrderModel();

        model.setId(UUID.randomUUID().toString());
        model.setOrderExternalReferenceId(String.valueOf(getRandomNumber(8)));
        model.setOrderStatus(OrderStatus.IN_CART);
        //model.setPurchasedOn(new Date());
        final OrderModel savedModel = orderRepository.save(model);

        request.getItemDetails().forEach(
                item -> {
                    OrderDetailsModel OrderDetailsModel = new OrderDetailsModel();
                    OrderDetailsModel.setId(UUID.randomUUID().toString());
                    OrderDetailsModel.setCreatedAt(new Date());
                    OrderDetailsModel.setItemId(item.getItemId().toString());
                    OrderDetailsModel.setQuantity(item.getQuantity());
                    OrderDetailsModel.setUserId(user.getId());
                    OrderDetailsModel.setOrderId(savedModel.getId());
                    orderDetailRepository.save(OrderDetailsModel);
                }
        );
        return model;
    }

    @Override
    public List<OrderResponse> getOrder(User user) {
        if (user.getProfileType().equals(ProfileType.DRIVER.name())) {
            return orderDetailRepository.findByDriverId(user.getId()).stream()
                    .map(this::getOrderResponse).collect(Collectors.toList());
        }
        // TODO: handle null
        return null;
    }

    public static int getRandomNumber(int n) {
        int m = (int) Math.pow(10, n - 1);
        return m + new Random().nextInt(9 * m);
    }

    private OrderResponse getOrderResponse(OrderModel orderModel){
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(orderModel.getId());
        orderResponse.setOrderExternalReferenceId(orderModel.getOrderExternalReferenceId());
        orderResponse.setOrderStatus(orderModel.getOrderStatus());
        orderResponse.setCreatedAt(orderModel.getCreatedAt());
        orderResponse.setModifiedAt(orderModel.getModifiedAt());
        orderResponse.setCreatedBy(orderModel.getCreatedBy());
        orderResponse.setModifiedBy(orderModel.getModifiedBy());
        return orderResponse;
    }

    private OrderResponse getOrderResponse(OrderDetailsModel orderDetailsModel) {

        OrderResponse orderResponse = new OrderResponse();
        OrderModel orderModel = orderRepository.findById(orderDetailsModel.getOrderId())
                .orElseThrow(IllegalArgumentException::new);

        orderResponse.setId(orderModel.getId());
        orderResponse.setOrderExternalReferenceId(orderModel.getOrderExternalReferenceId());
        orderResponse.setOrderStatus(orderModel.getOrderStatus());
        orderResponse.setCreatedAt(orderModel.getCreatedAt());
        orderResponse.setModifiedAt(orderModel.getModifiedAt());
        orderResponse.setCreatedBy(orderModel.getCreatedBy());
        orderResponse.setModifiedBy(orderModel.getModifiedBy());

        User customer = userRepository.findById(orderDetailsModel.getUserId())
                .orElseThrow(IllegalArgumentException::new);
        AddressModel destination = addressRepository.findById(customer.getAddressId())
                .orElseThrow(IllegalArgumentException::new);
        orderResponse.setDestination(destination);

        StoreModel storeModel = storeRepository.findById(orderModel.getStoreId())
                .orElseThrow(IllegalArgumentException::new);

        AddressModel origin = addressRepository.findById(storeModel.getAddressId())
                .orElseThrow(IllegalArgumentException::new);
        orderResponse.setDestination(origin);


        List<ItemResponse> items = orderDetailRepository.
                findByOrderId(orderModel.getId()).stream()
                .map(this::getItemResponse).
                        collect(Collectors.toList());

        orderResponse.setItems(items);
        return orderResponse;
    }

    private ItemResponse getItemResponse(OrderDetailsModel orderDetailsModel) {

        ItemResponse response = new ItemResponse();
        response.setId(orderDetailsModel.getItemId());

        ItemModel itemModel = itemRepository.findById(orderDetailsModel.getItemId())
                .orElseThrow(IllegalArgumentException::new);
        response.setName(itemModel.getName());
        response.setPrice(itemModel.getPrice());
        response.setType(itemModel.getType());
        response.setQuantity(orderDetailsModel.getQuantity());
        return response;
    }
}
