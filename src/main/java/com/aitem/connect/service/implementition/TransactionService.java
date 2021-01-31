package com.aitem.connect.service.implementition;

import com.aitem.connect.repository.*;
import com.aitem.connect.request.TransactionRequest;
import com.aitem.connect.request.AddressDetailRequest;
import com.aitem.connect.request.UpdateOrderRequest;
import com.aitem.connect.response.TransactionResponse;
import com.aitem.connect.mapper.AddressDetailMapper;
import com.aitem.connect.model.*;
import com.aitem.connect.enums.ProfileType;
import com.aitem.connect.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aitem.connect.service.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.text.SimpleDateFormat;

@Service
public class TransactionService implements Transaction {
    private TransactionRepository transactionRepository;
    private AddressDetailRepository addressDetailRepository;
    private OrderRepository orderRepository;
    private StoreRepository storeRepository;
    private RetailerUserRepository retailerUserRepository;
    private EventRepository eventRepository;
    private ItemRepository itemRepository;
    private OrderDAO orderDAO;

    private TransactionService(
        @Autowired TransactionRepository transactionRepository,
        @Autowired AddressDetailRepository addressDetailRepository,
        @Autowired OrderRepository orderRepository,
        @Autowired StoreRepository storeRepository,
        @Autowired RetailerUserRepository retailerUserRepository,
        @Autowired EventRepository eventRepository,
        @Autowired ItemRepository itemRepository,
        @Autowired OrderDAO orderDAO
    ) {
        this.transactionRepository = transactionRepository;
        this.addressDetailRepository = addressDetailRepository;
        this.orderRepository = orderRepository;
        this.storeRepository = storeRepository;
        this.retailerUserRepository = retailerUserRepository;
        this.eventRepository = eventRepository;
        this.itemRepository = itemRepository;
        this.orderDAO = orderDAO;
    }

    @Override
    public TransactionModel createTransaction(TransactionRequest request, User user) {
        if (user.getProfileType().equals(ProfileType.SHOPPER.name())) {
            OrderModel order = orderRepository.findById(request.getOrderId()).orElseThrow(() -> new IllegalArgumentException("Order not found"));

            if (order.getOrderStatus().equals(OrderStatus.CHECKED_OUT.name())) {
                UpdateOrderRequest req = new UpdateOrderRequest();
                req.setOrderId(request.getOrderId());
                req.setOrderStatus(OrderStatus.PAYMENT_SUCCESSFUL);
                order = orderDAO.updateOrderStatus(req, user);
                AddressDetailRequest billingAddressRequest = request.getBillingAddress();
                AddressDetailModel billingAddressModel = AddressDetailMapper.getAddressDetailModel(billingAddressRequest);
                billingAddressModel.setId(UUID.randomUUID().toString());
                billingAddressModel = addressDetailRepository.save(billingAddressModel);
                AddressDetailRequest deliverAddressRequest = request.getDeliverAddress();
                AddressDetailModel deliverAddressModel = AddressDetailMapper.getAddressDetailModel(deliverAddressRequest);
                deliverAddressModel.setId(UUID.randomUUID().toString());
                deliverAddressModel = addressDetailRepository.save(deliverAddressModel);
                TransactionModel transaction = new TransactionModel();
                transaction.setId(UUID.randomUUID().toString());
                transaction.setUserId(user.getId());
                transaction.setOrderId(request.getOrderId());
                transaction.setItemId(request.getItemId());
                transaction.setBillingAddress(billingAddressModel.getId());
                transaction.setDeliverAddress(deliverAddressModel.getId());
                transaction.setQuantity(request.getQuantity());
                transaction.setPrice(request.getPrice());
                transaction.setNote(request.getNote());
                transaction.setCreatedAt(new Date());
                transaction=transactionRepository.save(transaction);

                var status = updateEventCount(request.getItemId(), request.getQuantity());

                if(status == 1) {
                    return transaction;    
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private int updateEventCount(String itemId, long count) {
        ItemModel itemModel = itemRepository.findById(itemId).orElseThrow(IllegalArgumentException::new);
        StoreModel storeModel = storeRepository.findById(itemModel.getStoreId()).orElseThrow(IllegalArgumentException::new);

        if(storeModel.getType() == 1) {
            RetailerUserModel retailerModel = retailerUserRepository.findByStoreId(itemModel.getStoreId());
            EventModel event = eventRepository.findById(retailerModel.getUserId()).orElseThrow(IllegalArgumentException::new);
            Date nowDate = new Date();
            if(event.getStatus() == 1 && nowDate.before(event.getStopAt())) {
                if(event.getRemainCount() > count) {
                    event.setRemainCount(event.getRemainCount() - count);
                } else if(event.getRemainCount() == count) {
                    event.setRemainCount(0);
                    //event.setStatus(new Long(2));
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

    @Override
    public List<TransactionResponse> getTransactions(User user, String userId) {
        if (user.getProfileType().equals(ProfileType.SUPER.name())) {
            List<TransactionResponse> transactions = transactionRepository.findByUserId(userId).stream().map(this::getTransactionResponse).collect(Collectors.toList());;
            return transactions;
        } else {
            return null;
        }
    }

    private TransactionResponse getTransactionResponse(TransactionModel model) {
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setId(model.getId());
        transactionResponse.setCreatedAt(model.getCreatedAt());
        transactionResponse.setNote(model.getNote());
        transactionResponse.setPrice(model.getPrice());
        transactionResponse.setQuantity(model.getQuantity());
        ItemModel item = itemRepository.findById(model.getItemId()).orElseThrow(() -> new IllegalArgumentException("Item not found"));
        transactionResponse.setItemName(item.getName());
        AddressDetailModel billingAddress = addressDetailRepository.findById(model.getBillingAddress()).orElseThrow(IllegalArgumentException::new);
        AddressDetailModel deliveryAddress = addressDetailRepository.findById(model.getDeliverAddress()).orElseThrow(IllegalArgumentException::new);
        transactionResponse.setBillingAddress(billingAddress);
        transactionResponse.setDeliveryAddress(deliveryAddress);
        return transactionResponse;
    }
}