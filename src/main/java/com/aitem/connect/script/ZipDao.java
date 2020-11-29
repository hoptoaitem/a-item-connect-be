package com.aitem.connect.script;

import com.aitem.connect.enums.ProfileType;
import com.aitem.connect.model.*;
import com.aitem.connect.repository.*;
import com.aitem.connect.utils.DistanceCalculator;
import com.amazonaws.services.opsworks.model.UserProfile;
import com.amazonaws.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ZipDao {

    private ZipBulkRepository zipBulkRepository;
    private ZipRepository zipRepository;
    private ZipNeighbourRepository zipNeighbourRepository;
    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private ZipDriverRepository zipDriverRepository;
    private ZipStoreRepository zipStoreRepository;
    private StoreRepository storeRepository;

    public ZipDao(
            @Autowired ZipStoreRepository zipStoreRepository,
            @Autowired StoreRepository storeRepository,
            @Autowired ZipDriverRepository zipDriverRepository,
            @Autowired AddressRepository addressRepository,
            @Autowired UserRepository userRepository,
            @Autowired ZipBulkRepository zipBulkRepository,
            @Autowired ZipRepository zipRepository,
            @Autowired ZipNeighbourRepository zipNeighbourRepository
    ) {
        this.storeRepository = storeRepository;
        this.zipStoreRepository = zipStoreRepository;
        this.zipDriverRepository = zipDriverRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.zipBulkRepository = zipBulkRepository;
        this.zipRepository = zipRepository;
        this.zipNeighbourRepository = zipNeighbourRepository;
    }

    @Async("threadPoolTaskExecutor")
    public void mapZip() {

        zipRepository.findAll().forEach(zip -> {
            List<Zip> zipInStates = zipRepository.findByState(zip.getState());
            zipInStates.forEach(zipInState -> {
                double distance = DistanceCalculator.distance(
                        Double.valueOf(zip.getLat()),
                        Double.valueOf(zip.getLon()),
                        Double.valueOf(zipInState.getLat()),
                        Double.valueOf(zipInState.getLon()),
                        "M");
                if (distance <= 10) {
                    ZipNeighbour zipNeighbourItem = new ZipNeighbour();
                    zipNeighbourItem.setId(UUID.randomUUID().toString());
                    zipNeighbourItem.setZip(zip.getZip());
                    zipNeighbourItem.setNeighbourZip(zipInState.getZip());
                    zipNeighbourItem.setDistance(String.valueOf(distance));
                    zipNeighbourRepository.save(zipNeighbourItem);
                }
            });
        });
    }

    @Async("threadPoolTaskExecutor")
    public void loadZipDriver() {
        userRepository.findAll().forEach(user -> {
            if (user.getProfileType().equals(ProfileType.DRIVER.name())) {
                String addressId = user.getAddressId();
                Optional<AddressModel> addressOp = addressRepository.findById(addressId);
                if (addressOp.isPresent()) {
                    AddressModel addressModel = addressOp.get();
                    if (StringUtils.isNotEmpty(addressModel.getZip())) {
                        List<ZipNeighbour> allNeghbour = zipNeighbourRepository.findByZip(addressModel.getZip());
                        if (!CollectionUtils.isNullOrEmpty(allNeghbour)) {
                            allNeghbour.forEach(item -> {
                                ZipDriver zipDriverItem = new ZipDriver();
                                zipDriverItem.setId(UUID.randomUUID().toString());
                                zipDriverItem.setZip(addressModel.getZip());
                                zipDriverItem.setDriverId(user.getId());
                                // TODO add distance later after google map calculation
                                zipDriverRepository.save(zipDriverItem);
                            });
                        }
                    }
                }
            }
        });
    }

    @Async("threadPoolTaskExecutor")
    public void loadZipStore() {
        storeRepository.findAll().forEach(store -> {
            String addressId = store.getAddressId();
            if (StringUtils.isNotEmpty(addressId)) {
                Optional<AddressModel> addressOp = addressRepository.findById(addressId);
                if (addressOp.isPresent()) {
                    AddressModel addressModel = addressOp.get();
                    if (StringUtils.isNotEmpty(addressModel.getZip())) {
                        List<ZipNeighbour> allNeghbour = zipNeighbourRepository.findByZip(addressModel.getZip());
                        if (!CollectionUtils.isNullOrEmpty(allNeghbour)) {
                            allNeghbour.forEach(item -> {
                                ZipStore zipStoreItem = new ZipStore();
                                zipStoreItem.setId(UUID.randomUUID().toString());
                                zipStoreItem.setZip(addressModel.getZip());
                                zipStoreItem.setStoreId(store.getId());
                                // TODO add distance later after google map calculation
                                zipStoreRepository.save(zipStoreItem);
                            });
                        }
                    }
                }
            }
        });
    }
}