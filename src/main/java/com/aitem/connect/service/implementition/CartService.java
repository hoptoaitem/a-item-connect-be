package com.aitem.connect.service.implementition;

import com.aitem.connect.enums.CartStatus;
import com.aitem.connect.model.*;
import com.aitem.connect.repository.*;
import com.aitem.connect.request.CartRequest;
import com.aitem.connect.request.OrderItemDetails;
import com.aitem.connect.request.OrderRequest;
import com.aitem.connect.response.CartResponse;
import com.aitem.connect.response.ItemResponse;
import com.aitem.connect.service.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartService implements Cart {
    private CartRepository cartRepository;
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;
    private AddressRepository addressRepository;
    private UserRepository userRepository;
    private ItemRepository itemRepository;
    private StoreRepository storeRepository;
    private RetailerUserRepository retailerUserRepository;
    private EventRepository eventRepository;
    private OrderDAO orderDAO;
    
    private CartService(
            @Autowired OrderDAO orderDAO,
            @Autowired CartRepository cartRepository,
            @Autowired OrderRepository orderRepository,
            @Autowired OrderDetailRepository orderDetailRepository,
            @Autowired AddressRepository addressRepository,
            @Autowired UserRepository userRepository,
            @Autowired ItemRepository itemRepository,
            @Autowired StoreRepository storeRepository,
            @Autowired EventRepository eventRepository,
            @Autowired RetailerUserRepository retailerUserRepository
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
        this.eventRepository = eventRepository;
    }

    public CartResponse updateCart(CartRequest request, User user) {
        CartResponse response = new CartResponse();
        int status = updateEventCount(request.getItemId(), request.getQuantity());
        if(status == 1) {
            CartModel model = cartRepository.findByUserIdAndStatus(user.getId(), CartStatus.IN_PROGRESS.name());
            OrderModel order = null;
            if (Objects.isNull(model)) {
                order = orderDAO.createOrder(mapCartRequestToOrderRequest(request), user);
                response.setOrderId(order.getId());
                response.setStatus(CartStatus.IN_PROGRESS);
                List<OrderDetailsModel> orderDetails = orderDetailRepository.findByOrderId(order.getId());

                if (Objects.nonNull(orderDetails)) {
                    response.setItems(orderDetails.stream().map(this::getItemResponse).collect(Collectors.toList()));
                }

                CartModel cartModel = new CartModel();
                cartModel.setId(UUID.randomUUID().toString());
                cartModel.setOrderId(order.getId());
                cartModel.setStatus(CartStatus.IN_PROGRESS.name());
                cartModel.setUserId(user.getId());
                cartModel.setStoreId(order.getStoreId());
                cartRepository.save(cartModel);
                return response;
            }

            OrderModel oderModel = orderRepository.findById(model.getOrderId()).orElseThrow(IllegalArgumentException::new);

            order = orderDAO.updateOrder(oderModel, mapCartRequestToOrderRequest(request));
            // update order
            return response;
        } else {
            return null;
        }
    }

    private int updateEventCount(String itemId, long count) {
        ItemModel itemModel = itemRepository.findById(itemId).orElseThrow(IllegalArgumentException::new);
        StoreModel storeModel = storeRepository.findById(itemModel.getStoreId()).orElseThrow(IllegalArgumentException::new);

        if(storeModel.getType == 1) {
            RetailerUserModel retailerModel = retailerUserRepository.findByStoreId(itemModel.getStoreId()).orElseThrow(IllegalArgumentException::new);
            EventModel event = eventRepository.findById(retailerModel.getUserId()).orElseThrow(IllegalArgumentException::new);
            Date nowDate = new Date();
            if(event.getStatus() == 1 && nowDate.before(event.getStopAt())) {
                if(event.getRemainCount() > count) {
                    event.setRemainCount(event.getRemainCount() - count);
                } else if(event.getRemainCount() == count) {
                    event.setRemainCount(0);
                    event.setStatus(new Long(2));
                } else {
                    return 0;
                }
                eventRepository.save(event);
                return 1;
            } else {
                return 0;
            }
        } else {
            return 1;
        }
    }

    private OrderRequest mapCartRequestToOrderRequest(CartRequest request) {
        OrderRequest orderRequest = new OrderRequest();
        List<OrderItemDetails> items = new ArrayList<>();
        OrderItemDetails item = new OrderItemDetails();
        item.setItemId(request.getItemId());
        item.setQuantity(request.getQuantity());
        items.add(item);
        orderRequest.setItemDetails(items);
        return orderRequest;
    }

    public CartResponse getCart(User user) {
        CartResponse response = new CartResponse();
        CartModel model = cartRepository.findByUserIdAndStatus(user.getId(), CartStatus.IN_PROGRESS.name());
        if (Objects.isNull(model)) {
            response.setStatus(CartStatus.EMPTY);
            return response;
        }

        response.setId(model.getId());
        response.setStatus(CartStatus.valueOf(model.getStatus()));
        response.setOrderId(model.getOrderId());
        OrderModel order = orderRepository.findById(model.getOrderId()).orElseThrow(IllegalArgumentException::new);
        List<OrderDetailsModel> orderDetails = orderDetailRepository.findByOrderId(order.getId());

        if (Objects.nonNull(orderDetails)) {
            response.setItems(orderDetails.stream().map(this::getItemResponse).collect(Collectors.toList()));
        }

        String total = getTotal(response.getItems());
        response.setTotal(total);
        return response;
    }

    private String getTotal(List<ItemResponse> items) {
        return String.valueOf(items.stream().map(item -> Double.valueOf(item.getPrice()) * item.getQuantity()).mapToDouble(num -> num).sum());
    }

    private ItemResponse getItemResponse(OrderDetailsModel orderDetailsModel) {
        ItemResponse response = new ItemResponse();
        response.setId(orderDetailsModel.getItemId());
        ItemModel itemModel = itemRepository.findById(orderDetailsModel.getItemId()).orElseThrow(IllegalArgumentException::new);
        response.setName(itemModel.getName());
        response.setPrice(itemModel.getPrice());
        response.setType(itemModel.getType());
        response.setQuantity(orderDetailsModel.getQuantity());
        return response;
    }
}
