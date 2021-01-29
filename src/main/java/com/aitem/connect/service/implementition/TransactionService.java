package com.aitem.connect.service.implementition;

import com.aitem.connect.repository.*;
import com.aitem.connect.request.TransactionRequest;
import com.aitem.connect.request.AddressRequest;
import com.aitem.connect.mapper.AddressMapper;
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
    private ItemDAO itemDAO;

    private TransactionService(
        @Autowired TransactionRepository transactionRepository,
        @Autowired ItemDAO itemDAO,
        @Autowired AddressRepository addressRepository
    ) {
        this.transactionRepository = transactionRepository;
        this.itemDAO = itemDAO;
        this.addressRepository = addressRepository;
    }

    @Override
    public TransactionModel createTransaction(TransactionRequest request, User user) {
        if (user.getProfileType().equals(ProfileType.SHOPPER.name())) {
            AddressRequest billingAddressRequest = request.getBillingAddress();
            AddressModel billingAddressModel = AddressMapper.getAddressModel(billingAddressRequest);
            billingAddressModel.setId(UUID.randomUUID().toString());
            billingAddressModel = addressRepository.save(billingAddressModel);
            AddressRequest deliverAddressRequest = request.getDeliverAddress();
            AddressModel deliverAddressModel = AddressMapper.getAddressModel(deliverAddressRequest);
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