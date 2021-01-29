package com.aitem.connect.service.implementition;

import com.aitem.connect.repository.*;
import com.aitem.connect.request.TransactionRequest;
import com.aitem.connect.request.AddressDetailRequest;
import com.aitem.connect.request.UpdateOrderRequest;
import com.aitem.connect.mapper.AddressDetailMapper;
import com.aitem.connect.model.*;
import com.aitem.connect.enums.ProfileType;
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
    private AddressRepository addressRepository;
    private OrderRepository orderRepository;
    private ItemDAO itemDAO;
    private OrderDAO orderDAO;

    private TransactionService(
        @Autowired TransactionRepository transactionRepository,
        @Autowired ItemDAO itemDAO,
        @Autowired AddressRepository addressRepository,
        @Autowired OrderRepository orderRepository,
        @Autowired OrderDAO orderDAO
    ) {
        this.transactionRepository = transactionRepository;
        this.itemDAO = itemDAO;
        this.addressRepository = addressRepository;
        this.orderRepository = orderRepository;
        this.orderDAO = orderDAO;
    }

    @Override
    public TransactionModel createTransaction(TransactionRequest request, User user) {
        if (user.getProfileType().equals(ProfileType.SHOPPER.name())) {
            OrderModel order = orderRepository.findById(request.getOrderId());

            if (order.getOrderStatus().equals(OrderStatus.CHECKED_OUT.name())) {
                UpdateOrderRequest req = new UpdateOrderRequest();
                req.setOrderStatus(OrderStatus.PAYMENT_SUCCESSFUL.name());
                order = orderDAO.updateOrderStatus(req, user);
                AddressDetailRequest billingAddressRequest = request.getBillingAddress();
                AddressDetailModel billingAddressModel = AddressDetailMapper.getAddressDetailModel(billingAddressRequest);
                billingAddressModel.setId(UUID.randomUUID().toString());
                billingAddressModel = addressRepository.save(billingAddressModel);
                AddressDetailRequest deliverAddressRequest = request.getDeliverAddress();
                AddressDetailModel deliverAddressModel = AddressDetailMapper.getAddressDetailModel(deliverAddressRequest);
                deliverAddressModel.setId(UUID.randomUUID().toString());
                deliverAddressModel = addressRepository.save(deliverAddressModel);
                TransactionModel transaction = new TransactionModel();
                transaction.setId(UUID.randomUUID().toString());
                transaction.setUserId(user.getId());
                transaction.setOrderId(request.getOrderId());
                transaction.setItemId(request.getItemId());
                transaction.setBillingAddress(billingAddressModel.getId());
                transaction.setDeliverAddress(deliverAddressModel.getId());
                transaction.setQuantity(request.getQuantity());
                transaction.setPrice(request.getPrice());
                transaction.setCreatedAt(new Date());
                return transaction;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public List<TransactionModel> getTransactions(User user, String userId) {
        if (user.getProfileType().equals(ProfileType.SUPER.name())) {
            List<TransactionModel> transactions = transactionRepository.findByUserId(user.getId());
            return transactions;
        } else {
            return null;
        }
    }
}