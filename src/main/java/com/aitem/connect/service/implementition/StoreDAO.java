package com.aitem.connect.service.implementition;

import com.aitem.connect.model.AddressModel;
import com.aitem.connect.model.StoreModel;
import com.aitem.connect.model.User;
import com.aitem.connect.model.ZipStore;
import com.aitem.connect.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StoreDAO {

    private ItemRepository itemRepository;
    private RetailerUserRepository retailerUserRepository;
    private ZipStoreRepository zipStoreRepository;
    private AddressRepository addressRepository;
    private StoreRepository storeRepository;

    private StoreDAO(
            @Autowired ItemRepository itemRepository,
            @Autowired RetailerUserRepository retailerUserRepository,
            @Autowired ZipStoreRepository zipStoreRepository,
            @Autowired AddressRepository addressRepository,
            @Autowired StoreRepository storeRepository
    ) {
        this.itemRepository = itemRepository;
        this.retailerUserRepository = retailerUserRepository;
        this.zipStoreRepository = zipStoreRepository;
        this.addressRepository = addressRepository;
        this.storeRepository = storeRepository;
    }


    public List<StoreModel> getStoreForUser(User user) {

        List<StoreModel> storeList = new ArrayList<>();

        AddressModel addressModel = addressRepository.findById(user.getAddressId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Address not found"));

        List<ZipStore> storesInZip = zipStoreRepository
                .findByZip(addressModel.getZip());

        if (storesInZip != null && storesInZip.isEmpty()) {
            storeList = storesInZip.stream()
                    .map(storeInZip -> storeInZip.getStoreId())
                    .distinct()
                    .map(storeId -> storeRepository.findById(storeId)
                            .orElseThrow(
                                    () -> new IllegalArgumentException("Store not found")))
                    .collect(Collectors.toList());
        }
        return storeList;
    }
}