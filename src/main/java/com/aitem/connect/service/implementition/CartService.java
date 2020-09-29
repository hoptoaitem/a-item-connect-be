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
    }


    public CartResponse updateCart(CartRequest request, User user) {

        CartResponse response = new CartResponse();
        CartModel model = cartRepository.findByUserIdAndStatus(user.getId()
                , CartStatus.IN_PROGRESS.name());
        OrderModel order = null;
        if (Objects.isNull(model)) {
            order = orderDAO.createOrder(mapCartRequestToOrderRequest(request),
                    user);
            response.setOrderId(order.getId());
            response.setStatus(CartStatus.IN_PROGRESS);

            List<OrderDetailsModel> orderDetails
                    = orderDetailRepository.findByOrderId(order.getId());

            if (Objects.nonNull(orderDetails)) {
                response.setItems(orderDetails.stream()
                        .map(this::getItemResponse).collect(Collectors.toList()));
            }

            // create cart
            /*
            	@Id




	private Date createdAt;
	private Date modifiedAt;
	private String createdBy;
	private String modifiedBy;
             */

            CartModel cartModel = new CartModel();
            cartModel.setId(UUID.randomUUID().toString());
            cartModel.setOrderId(order.getId());
            cartModel.setStatus(CartStatus.IN_PROGRESS.name());
            cartModel.setUserId(user.getId());
            cartModel.setStoreId(order.getStoreId());

            cartRepository.save(cartModel);


            return response;
        }
        OrderModel oderModel = orderRepository.findById(model.getOrderId())
                .orElseThrow(IllegalArgumentException::new);

        order = orderDAO.updateOrder(oderModel, mapCartRequestToOrderRequest(request));
        // update order
        return response;
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
        CartModel model = cartRepository.findByUserIdAndStatus(user.getId()
                , CartStatus.IN_PROGRESS.name());
        if (Objects.isNull(model)) {
            response.setStatus(CartStatus.EMPTY);
            return response;
        }

        response.setId(model.getId());
        response.setStatus(CartStatus.valueOf(model.getStatus()));
        response.setOrderId(model.getOrderId());

        OrderModel order = orderRepository.findById(model.getOrderId())
                .orElseThrow(IllegalArgumentException::new);
        List<OrderDetailsModel> orderDetails
                = orderDetailRepository.findByOrderId(order.getId());

        if (Objects.nonNull(orderDetails)) {
            response.setItems(orderDetails.stream()
                    .map(this::getItemResponse).collect(Collectors.toList()));
        }
        return response;
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
