package com.aitem.connect.repository;

import com.aitem.connect.model.ItemModel;
import com.aitem.connect.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ItemRepository extends JpaRepository<ItemModel, String> {

    List<ItemModel> findByStoreId(String storeId);
}
