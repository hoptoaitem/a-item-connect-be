package com.aitem.connect.controller;

import com.aitem.connect.model.ItemModel;
import com.aitem.connect.request.ItemRequest;
import com.aitem.connect.service.implementition.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class ItemController {
    /*

    private ItemService service;

    private ItemController(
            @Autowired ItemService service) {
        this.service = service;
    }

    @GetMapping(path = "/items", consumes = "application/json", produces = "application/json")
    public List<ItemModel> getItems(@RequestHeader("api-key-token")String key,
                                     @RequestParam("store_id") String storeId)
    {
        // TODO : validate user type
        return service.getItems(storeId);
    }

    // TODO : pass as list
    @PostMapping(path = "/items", consumes = "application/json", produces = "application/json")
    public UUID createItems(@RequestHeader("api-key-token") String key,
                            @RequestBody ItemRequest request) {

        ItemModel model = service.createItem(request);
        return UUID.fromString(model.getId());
    }

     */
}
