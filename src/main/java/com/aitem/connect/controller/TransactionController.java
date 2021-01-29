package com.aitem.connect.controller;

import com.aitem.connect.model.Authentication;
import com.aitem.connect.model.User;
import com.aitem.connect.model.TransactionModel;
import com.aitem.connect.repository.AuthenticationRepository;
import com.aitem.connect.repository.UserRepository;
import com.aitem.connect.service.implementition.TransactionService;
import com.aitem.connect.request.TransactionRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(tags = "Transaction API")
@CrossOrigin
@RestController
@RequestMapping("/")
public class TransactionController {
    private TransactionService service;
    private AuthenticationRepository authenticationRepository;
    private UserRepository userRepository;

    private TransactionController(
        @Autowired TransactionService service,
        @Autowired AuthenticationRepository authenticationRepository,
        @Autowired UserRepository userRepository) {
            this.service = service;
            this.authenticationRepository = authenticationRepository;
            this.userRepository = userRepository;
        }

    @ApiOperation(value = "Creat Transaction For the shopper.")
    @PostMapping(path = "/transactions", consumes = "application/json", produces = "application/json")
    public UUID createTransaction(@RequestHeader("api-key-token") String key, @RequestBody TransactionRequest request) {
        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        TransactionModel model = service.createTransaction(request, user);
        return UUID.fromString(model.getId());
    }

    @ApiOperation(value = "Get the transactions for the shopper.")
    @GetMapping(path = "/transactions/{user-id}", consumes = "application/json", produces = "application/json")
    public List<TransactionModel> getTransactions(@RequestHeader("api-key-token") String key, @PathVariable("user-id") String userId) {
        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return service.getTransactions(user, userId);
    }
}
