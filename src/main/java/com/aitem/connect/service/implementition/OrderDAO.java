package com.aitem.connect.service.implementition;

import com.aitem.connect.enums.CartStatus;
import com.aitem.connect.enums.OrderStatus;
import com.aitem.connect.enums.ProfileType;
import com.aitem.connect.model.*;
import com.aitem.connect.repository.*;
import com.aitem.connect.request.OrderItemDetails;
import com.aitem.connect.request.OrderRequest;
import com.aitem.connect.request.UpdateOrderRequest;
import com.aitem.connect.response.ItemResponse;
import com.aitem.connect.response.OrderResponse;
import com.aitem.connect.service.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class OrderDAO {

    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;
    private AddressRepository addressRepository;
    private UserRepository userRepository;
    private ItemRepository itemRepository;
    private StoreRepository storeRepository;
    private RetailerUserRepository retailerUserRepository;
    private CartRepository cartRepository;


    private OrderDAO(
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

    public OrderModel createOrder(OrderRequest request, User user) {
        OrderModel model = new OrderModel();
        model.setId(UUID.randomUUID().toString());
        model.setOrderExternalReferenceId(String.valueOf(getRandomNumber(8)));
        model.setOrderStatus(OrderStatus.IN_CART);
        //model.setPurchasedOn(new Date());
        model.setUserId(user.getId());
        final OrderModel savedModel = orderRepository.save(model);
        saveOrderDetails(savedModel, request.getItemDetails());
        return savedModel;
    }

    private void saveOrderDetails(OrderModel currentModel,
                                  List<OrderItemDetails> itemDetails) {
        itemDetails.forEach(
                item -> {
                    OrderDetailsModel OrderDetailsModel = new OrderDetailsModel();
                    OrderDetailsModel.setId(UUID.randomUUID().toString());
                    OrderDetailsModel.setCreatedAt(new Date());
                    OrderDetailsModel.setItemId(item.getItemId().toString());
                    OrderDetailsModel.setQuantity(item.getQuantity());
                    OrderDetailsModel.setOrderId(currentModel.getId());
                    orderDetailRepository.save(OrderDetailsModel);
                });
    }

    public List<OrderResponse> getOrder(User user) {
        if (user.getProfileType().equals(ProfileType.DRIVER.name())) {
            return orderRepository.findByDriverId(user.getId()).stream()
                    .map(this::getOrderResponse).collect(Collectors.toList());
        }
        // shoppers
        if (user.getProfileType().equals(ProfileType.RETAILER.name()) ){
            List<RetailerUserModel> retailerUserModels
                    = retailerUserRepository.findByUserId(user.getId());

            return retailerUserModels.stream().map
                    (item -> storeRepository.findById(item.getStoreId())
                            .orElseThrow(IllegalArgumentException::new))
                    .map(item -> orderRepository.findById(item.getId())
                            .orElseThrow(IllegalArgumentException::new))
                    .map(this::getOrderResponse).collect(Collectors.toList());
        }
        // customers
        return orderRepository.findByUserId(user.getId()).stream()
                .map(this::getOrderResponse).collect(Collectors.toList());
    }

    public static int getRandomNumber(int n) {
        int m = (int) Math.pow(10, n - 1);
        return m + new Random().nextInt(9 * m);
    }


    private OrderResponse getOrderResponse(OrderModel orderModel) {

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(orderModel.getId());
        orderResponse.setOrderExternalReferenceId(orderModel.getOrderExternalReferenceId());
        orderResponse.setOrderStatus(orderModel.getOrderStatus());
        orderResponse.setCreatedAt(orderModel.getCreatedAt());
        orderResponse.setModifiedAt(orderModel.getModifiedAt());
        orderResponse.setCreatedBy(orderModel.getCreatedBy());
        orderResponse.setModifiedBy(orderModel.getModifiedBy());

        User customer = userRepository.findById(orderModel.getUserId())
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

    public OrderModel updateOrder(OrderModel model, OrderRequest orderRequest) {
        saveOrderDetails(model, orderRequest.getItemDetails());
        return model;
    }

    public OrderModel updateOrderStatus(UpdateOrderRequest request, User user) {

        OrderModel orderModel = orderRepository.findById(request.getOrderId())
                .orElseThrow(IllegalArgumentException::new);

        orderModel.setOrderStatus(request.getOrderStatus());
        if (request.getOrderStatus() == OrderStatus.CHECKED_OUT) {
            CartModel cartModel = cartRepository.findByOrderId(request.getOrderId());
            cartModel.setStatus(CartStatus.COMPLETE);
            cartRepository.save(cartModel);
        }
        return orderRepository.save(orderModel);
    }
}
