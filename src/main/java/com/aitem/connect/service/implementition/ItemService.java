package com.aitem.connect.service.implementition;

import com.aitem.connect.model.ItemModel;
import com.aitem.connect.repository.ItemRepository;
import com.aitem.connect.request.ItemRequest;
import com.aitem.connect.service.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ItemService implements Item {

    private ItemRepository itemRepository;

    private ItemService(
            @Autowired ItemRepository itemRepository
    ) {
        this.itemRepository = itemRepository;
    }

    @Override
    public ItemModel createItem(ItemRequest request) {

        ItemModel model = getModel(request);
        model.setId(UUID.randomUUID().toString());
        model.setCreatedAt(new Date());
        model.setModifiedAt(new Date());
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
}
