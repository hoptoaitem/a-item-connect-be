package com.aitem.connect.repository;

import com.aitem.connect.model.OrderModel;
import com.aitem.connect.model.StoreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


public interface StoreRepository extends JpaRepository<StoreModel, String> {

}
