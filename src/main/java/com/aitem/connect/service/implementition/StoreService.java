package com.aitem.connect.service.implementition;

import com.aitem.connect.enums.ProfileType;
import com.aitem.connect.mapper.AddressMapper;
import com.aitem.connect.model.*;
import com.aitem.connect.repository.AddressRepository;
import com.aitem.connect.repository.OrderRepository;
import com.aitem.connect.repository.RetailerUserRepository;
import com.aitem.connect.repository.StoreRepository;
import com.aitem.connect.request.AddressRequest;
import com.aitem.connect.request.StoreRequest;
import com.aitem.connect.response.OrderResponse;
import com.aitem.connect.response.StoreResponse;
import com.aitem.connect.service.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StoreService implements Store {

    private OrderRepository orderRepository;
    private StoreRepository storeRepository;
    private AddressRepository addressRepository;
    private RetailerUserRepository retailerUserRepository;

    private StoreService(
            @Autowired StoreRepository storeRepository,
            @Autowired AddressRepository addressRepository,
            @Autowired RetailerUserRepository retailerUserRepository,
            @Autowired OrderRepository orderRepository
    ) {
        this.storeRepository = storeRepository;
        this.addressRepository = addressRepository;
        this.retailerUserRepository = retailerUserRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public StoreModel createStore(StoreRequest request, User user) {
        // TODO : replace with all builders
        AddressRequest addressRequest = request.getAddress();
        AddressModel addressModel = AddressMapper.getAddressModel(addressRequest);
        addressModel.setId(UUID.randomUUID().toString());
        addressModel = addressRepository.save(addressModel);

        StoreModel storeModel = new StoreModel();
        storeModel.setId(UUID.randomUUID().toString());
        storeModel.setAddressId(addressModel.getId());
        storeModel.setPhoneNo(request.getPhone());
        storeModel.setRetailerUserId(request.getRetailerUserId());
        storeModel = storeRepository.save(storeModel);

        if (user.getProfileType().equals(ProfileType.RETAILER.name())) {
            RetailerUserModel retailerUserModel = new RetailerUserModel();
            retailerUserModel.setId(UUID.randomUUID().toString());
            retailerUserModel.setUserId(user.getId());
            retailerUserModel.setStoreId(storeModel.getId());
            retailerUserModel.setCreatedAt(new Date());
            retailerUserModel.setModifiedAt(new Date());
            retailerUserRepository.save(retailerUserModel);
        }

        return storeModel;
    }



    public List<StoreResponse> getStores(User user) {

        List<StoreModel> storeList = new ArrayList<>();
        if (user.getProfileType().equals(ProfileType.RETAILER.name())) {
            List<RetailerUserModel> retailerUserModelList
                    = retailerUserRepository.findByUserId(user.getId());

            for (RetailerUserModel item : retailerUserModelList){
                StoreModel model = storeRepository.findById(item.getStoreId())
                        .orElseThrow(IllegalArgumentException::new);
                storeList.add(model);
            }
        } else {
            storeList.addAll(storeRepository.findAll());
        }
        return storeList.stream().map(this::getStoreResponse).collect(Collectors.toList());
    }

    private StoreResponse getStoreResponse(StoreModel storeModel) {

        StoreResponse storeResponse = new StoreResponse();
        storeResponse.setId(storeModel.getId());

        storeResponse.setAddress(addressRepository.findById(storeModel.getAddressId()).
                orElseThrow(IllegalArgumentException::new));
        storeResponse.setPhoneNo(storeResponse.getPhoneNo());
        storeResponse.setCreatedAt(storeModel.getCreatedAt());
        storeResponse.setModifiedAt(storeModel.getModifiedAt());
        storeResponse.setCreatedBy(storeModel.getCreatedBy());
        storeResponse.setModifiedBy(storeModel.getModifiedBy());
        return storeResponse;
    }

    public List<OrderResponse> getOrdersByStores(String storeId) {

        List<OrderModel> storeOrders = orderRepository.findByStoreId(storeId);

        List<OrderResponse> items =
                storeOrders.stream().map(model -> {
                    OrderResponse orderResponse = new OrderResponse();
                    OrderModel orderModel = orderRepository.findById(model.getId())
                            .orElseThrow(IllegalArgumentException::new);

                    orderResponse.setId(orderModel.getId());
                    orderResponse.setOrderExternalReferenceId(orderModel.getOrderExternalReferenceId());
                    orderResponse.setOrderStatus(orderModel.getOrderStatus());
                    orderResponse.setCreatedAt(orderModel.getCreatedAt());
                    orderResponse.setModifiedAt(orderModel.getModifiedAt());
                    orderResponse.setCreatedBy(orderModel.getCreatedBy());
                    orderResponse.setModifiedBy(orderModel.getModifiedBy());

                    return orderResponse;
                }).collect(Collectors.toList());

        return items;
    }
}
