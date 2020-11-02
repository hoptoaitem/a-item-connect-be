package com.aitem.connect.service.implementition;

import com.aitem.connect.enums.ProfileType;
import com.aitem.connect.helper.AitemConnectHelper;
import com.aitem.connect.model.ItemModel;
import com.aitem.connect.model.RetailerUserModel;
import com.aitem.connect.model.User;
import com.aitem.connect.repository.ItemRepository;
import com.aitem.connect.repository.RetailerUserRepository;
import com.aitem.connect.request.ItemRequest;
import com.aitem.connect.service.Item;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ItemDAO {

    private ItemRepository itemRepository;
    private RetailerUserRepository retailerUserRepository;


    private ItemDAO(
            @Autowired ItemRepository itemRepository,
            @Autowired RetailerUserRepository retailerUserRepository
    ) {
        this.itemRepository = itemRepository;
        this.retailerUserRepository = retailerUserRepository;
    }

    public ItemModel createItem(ItemRequest request, String storeId) {

        ItemModel model = getModel(request);
        model.setId(UUID.randomUUID().toString());
        model.setCreatedAt(new Date());
        model.setModifiedAt(new Date());

        if (!StringUtils.isNotBlank(storeId)) {
            throw new IllegalArgumentException("Store cannot be empty");
        }
        model.setStoreId(storeId);
        model = itemRepository.save(model);
        return model;
    }

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
        model.setPictureId(request.getPictureId());
        model.setExternalRefId(
                String.valueOf(AitemConnectHelper.getRandomNumber(8)));
        return model;
    }
}
