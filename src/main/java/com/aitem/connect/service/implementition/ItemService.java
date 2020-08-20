package com.aitem.connect.service.implementition;

import com.aitem.connect.enums.ProfileType;
import com.aitem.connect.model.ItemModel;
import com.aitem.connect.model.RetailerUserModel;
import com.aitem.connect.model.StoreModel;
import com.aitem.connect.model.User;
import com.aitem.connect.repository.ItemRepository;
import com.aitem.connect.repository.RetailerUserRepository;
import com.aitem.connect.request.ItemRequest;
import com.aitem.connect.service.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ItemService implements Item {

    private ItemRepository itemRepository;
    private RetailerUserRepository retailerUserRepository;


    private ItemService(
            @Autowired ItemRepository itemRepository,
            @Autowired RetailerUserRepository retailerUserRepository
    ) {
        this.itemRepository = itemRepository;
        this.retailerUserRepository = retailerUserRepository;
    }

    @Override
    public ItemModel createItem(ItemRequest request, User user) {

        ItemModel model = getModel(request);
        model.setId(UUID.randomUUID().toString());
        model.setCreatedAt(new Date());
        model.setModifiedAt(new Date());

        if (user.getProfileType().equals(ProfileType.RETAILER.name())) {
            RetailerUserModel retailerUserModel
                    = retailerUserRepository.findByUserId(user.getId()).stream().findAny()
                    .orElseThrow(IllegalArgumentException::new);
            model.setStoreId(retailerUserModel.getStoreId());
        }
        model = itemRepository.save(model);
        return model;
    }

    @Override
    public List<ItemModel> getItems(String storeId) {
        return itemRepository.findByStoreId(storeId);
    }

    private ItemModel getModel(ItemRequest request) {

        ItemModel model = new ItemModel();
        model.setType(request.getType());
        model.setName(request.getName());
        model.setPrice(request.getPrice());
        model.setQuantity(request.getQuantity());
        model.setStatus(request.getStatus().name());
        model.setWebsite(request.getWebsite());
        model.setDescription(request.getDescription());
        model.setShortDescription(request.getShortDescription());
        model.setSku(request.getSku());
        model.setWeight(request.getWeight());
        model.setVisibility(request.getVisibility());
        model.setStoreId(request.getStoreId());

        return model;
    }

    public List<ItemModel> getItems(User user) {

        if (user.getProfileType().equals(ProfileType.RETAILER.name())) {

            List<RetailerUserModel> retailerUserModel
                    = retailerUserRepository.findByUserId(user.getId())
                    .stream()
                    .collect(Collectors.toList());


            List<String> stores = retailerUserModel.stream()
                    .map(RetailerUserModel::getStoreId).collect(Collectors.toList());

            return stores.stream().map(item -> itemRepository.findByStoreId(item))
                    .collect(Collectors.toList())
                    .stream()
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }
        return null;
    }
}
