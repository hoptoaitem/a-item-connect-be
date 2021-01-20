package com.aitem.connect.service.implementition;

import com.aitem.connect.enums.CartStatus;
import com.aitem.connect.enums.OrderStatus;
import com.aitem.connect.enums.ProfileType;
import com.aitem.connect.helper.AitemConnectHelper;
import com.aitem.connect.model.*;
import com.aitem.connect.processor.OrderProcessor;
import com.aitem.connect.repository.*;
import com.aitem.connect.request.OrderItemDetails;
import com.aitem.connect.request.OrderRequest;
import com.aitem.connect.request.UpdateOrderRequest;
import com.aitem.connect.response.*;
import com.aitem.connect.utils.NotificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.text.SimpleDateFormat;

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
    private NotificationUtils notificationUtils;
    private OrderProcessor orderProcessor;

    private OrderDAO(
            @Autowired OrderRepository orderRepository,
            @Autowired OrderDetailRepository orderDetailRepository,
            @Autowired AddressRepository addressRepository,
            @Autowired UserRepository userRepository,
            @Autowired ItemRepository itemRepository,
            @Autowired StoreRepository storeRepository,
            @Autowired RetailerUserRepository retailerUserRepository,
            @Autowired CartRepository cartRepository,
            @Autowired NotificationUtils notificationUtils,
            @Autowired OrderProcessor orderProcessor
    ) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.storeRepository = storeRepository;
        this.retailerUserRepository = retailerUserRepository;
        this.cartRepository = cartRepository;
        this.notificationUtils = notificationUtils;
        this.orderProcessor = orderProcessor;
    }

    public OrderModel createOrder(OrderRequest request, User user) {
        OrderModel model = new OrderModel();
        model.setId(UUID.randomUUID().toString());
        model.setOrderExternalReferenceId
                (String.valueOf(AitemConnectHelper.getRandomNumber(8)));
        model.setOrderStatus(OrderStatus.IN_CART.name());
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
        String created_date = format.format(new Date());
        model.setHistory("Created At: " + created_date);
        //model.setPurchasedOn(new Date());
        model.setUserId(user.getId());

        ItemModel itemModel = itemRepository.findById(request.getItemDetails().stream()
                .findAny().get().getItemId())
                .orElseThrow(IllegalArgumentException::new);

        model.setStoreId(itemModel.getStoreId());

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
                    .filter(x -> !x.getOrderStatus().equals(OrderStatus.DELIVERED.name()))
                    .map(this::getOrderResponse)
                    .collect(Collectors.toList());
        }
        // shoppers
        if (user.getProfileType().equals(ProfileType.RETAILER.name())) {
            List<RetailerUserModel> retailerUserModels
                    = retailerUserRepository.findByUserId(user.getId());

            List<StoreModel> storeModels =
                    retailerUserModels.stream().map
                            (retailerUserModel -> storeRepository
                                    .findById(retailerUserModel.getStoreId())
                                    .orElseThrow(IllegalArgumentException::new))
                            .collect(Collectors.toList());

            List<OrderModel> orderModels = storeModels.stream().map(
                    storeModel -> orderRepository
                            .findByStoreId(storeModel.getId())
            ).flatMap(Collection::stream)
                    .collect(Collectors.toList());

            return orderModels
                    .stream()
                    .map(this::getOrderResponse)
                    .collect(Collectors.toList());
        }
        // customers
        return orderRepository.findByUserId(user.getId()).stream()
                .map(this::getOrderResponse).collect(Collectors.toList());
    }

    public List<OrderResponse> getOrderHistory(User user) {
        if (user.getProfileType().equals(ProfileType.DRIVER.name())) {
            return orderRepository.findByDriverId(user.getId()).stream()
                    .map(this::getOrderResponse)
                    .filter(x -> x.getOrderStatus() == OrderStatus.DELIVERED)
                    .collect(Collectors.toList());
        }
        // shoppers
        if (user.getProfileType().equals(ProfileType.RETAILER.name())) {
            List<RetailerUserModel> retailerUserModels
                    = retailerUserRepository.findByUserId(user.getId());

            List<StoreModel> storeModels =
                    retailerUserModels.stream().map
                            (retailerUserModel -> storeRepository
                                    .findById(retailerUserModel.getStoreId())
                                    .orElseThrow(IllegalArgumentException::new))
                            .collect(Collectors.toList());

            List<OrderModel> orderModels = storeModels.stream().map(
                    storeModel -> orderRepository
                            .findByStoreId(storeModel.getId())
            ).flatMap(Collection::stream)
                    .collect(Collectors.toList());

            return orderModels
                    .stream()
                    .map(this::getOrderResponse)
                    .filter(x -> x.getOrderStatus() == OrderStatus.DELIVERED)
                    .collect(Collectors.toList());
        }
        // customers
        return orderRepository.findByUserId(user.getId()).stream()
                .map(this::getOrderResponse)
                .filter(x -> x.getOrderStatus() == OrderStatus.DELIVERED)
                .collect(Collectors.toList());
    }

    private OrderResponse getOrderResponse(OrderModel orderModel) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(orderModel.getId());
        orderResponse.setOrderExternalReferenceId(orderModel.getOrderExternalReferenceId());
        orderResponse.setOrderStatus(
                OrderStatus.valueOf(orderModel.getOrderStatus()));
        orderResponse.setHistory(orderModel.getHistory());
        orderResponse.setCreatedAt(orderModel.getCreatedAt());
        orderResponse.setModifiedAt(orderModel.getModifiedAt());
        orderResponse.setCreatedBy(orderModel.getCreatedBy());
        orderResponse.setModifiedBy(orderModel.getModifiedBy());

        User customer = userRepository.findById(orderModel.getUserId())
                .orElseThrow(IllegalArgumentException::new);
        AddressModel destination = addressRepository.findById(customer.getAddressId())
                .orElseThrow(IllegalArgumentException::new);
        orderResponse.setCustomerAddress(destination);

        StoreModel storeModel = storeRepository.findById(orderModel.getStoreId())
                .orElseThrow(IllegalArgumentException::new);

        AddressModel origin = addressRepository.findById(storeModel.getAddressId())
                .orElseThrow(IllegalArgumentException::new);
        orderResponse.setShopAddress(origin);

        // TODO: break to methods
        Contact contact = new Contact();

        Retailor retailor = new Retailor();
        Shopper shopper = new Shopper();

        retailor.setEmail(storeModel.getEmail());
        retailor.setPhone(storeModel.getPhoneNo());

        shopper.setEmail(customer.getEmail());
        shopper.setPhone(customer.getPhone());

        contact.setRetailor(retailor);
        contact.setShopper(shopper);


        orderResponse.setContact(contact);
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
        response.setQuantity(orderDetailsModel.getQuantity().intValue());
        return response;
    }

    public OrderModel updateOrder(OrderModel model, OrderRequest orderRequest) {
        saveOrderDetails(model, orderRequest.getItemDetails());
        return model;
    }

    public OrderModel updateOrderStatus(UpdateOrderRequest request, User user) {
        OrderModel orderModel = orderRepository.findById(request.getOrderId())
                .orElseThrow(IllegalArgumentException::new);

        orderModel.setOrderStatus(request.getOrderStatus().name());
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
        String updated_date = format.format(new Date());

        if(request.getOrderStatus() == OrderStatus.DELIVERED) {
            orderModel.setHistory(orderModel.getHistory() + "; Delivered At: " + updated_date);
        }

        if(request.getOrderStatus() == OrderStatus.CHECKED_OUT) {
            orderModel.setHistory(orderModel.getHistory() + "; Checked Out At: " + updated_date);
        }

        if(request.getOrderStatus() == OrderStatus.SUBMITTED_FOR_PAYMENT) {
            orderModel.setHistory(orderModel.getHistory() + "; Submitted At: " + updated_date);
        }

        if(request.getOrderStatus() == OrderStatus.PAYMENT_SUCCESSFUL) {
            orderModel.setHistory(orderModel.getHistory() + "; Successed At: " + updated_date);
        }

        if(request.getOrderStatus() == OrderStatus.DRIVER_DECLINED) {
            orderModel.setHistory(orderModel.getHistory() + "; Declined At: " + updated_date);
        }

        // case OrderStatus.CLERK_ASSIGNED_ORDER_TO_DRIVER:
        //     orderModel.setHistory(orderModel.getHistory() + "; Declined At: " + updated_date);
        //     break;

        
        if (request.getOrderStatus() == OrderStatus.CHECKED_OUT) {
            CartModel cartModel = cartRepository.findByOrderId(request.getOrderId());
            cartModel.setStatus(CartStatus.COMPLETE.name());
            cartRepository.save(cartModel);
        }

        if (request.getOrderStatus() == OrderStatus.DRIVER_ACCEPTED
                && orderModel.getDriverId().equals(user.getId())) {
            orderModel.setOrderStatus(OrderStatus.DRIVER_ACCEPTED.name());
            orderModel.setHistory(orderModel.getHistory() + "; Driver Accepted At: " + updated_date);
        } else if (request.getOrderStatus() == OrderStatus.DRIVER_ACCEPTED
                && !orderModel.getDriverId().equals(user.getId())) {
            orderModel.setHistory(orderModel.getHistory() + "; Expired At: " + updated_date);
            throw new IllegalStateException("Order Expired");
        }

        if (request.getOrderStatus() == OrderStatus.PAYMENT_SUCCESSFUL) {
            orderModel.setOrderStatus(OrderStatus.PAYMENT_SUCCESSFUL.name());
            orderProcessor.handleDriverForOrder(orderModel);
        }
        return orderRepository.save(orderModel);
    }
}
