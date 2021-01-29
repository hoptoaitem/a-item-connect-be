package com.aitem.connect.service;

import com.aitem.connect.model.TransactionModel;
import com.aitem.connect.model.User;
import com.aitem.connect.request.TransactionRequest;
import java.util.List;

public interface Transaction {
    TransactionModel createTransaction(TransactionRequest request, User user);
    List<TransactionModel> getTransactions(User user, String userId);
}