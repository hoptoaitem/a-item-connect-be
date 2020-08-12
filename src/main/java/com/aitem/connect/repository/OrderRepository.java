package com.aitem.connect.repository;

import com.aitem.connect.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OrderRepository extends JpaRepository<OrderModel, String> {

    List<OrderModel> findByDriverId(@Param("driverId") String driverId);

    List<OrderModel> findByStoreId(@Param("storeId") String storeId);

    List<OrderModel> findByUserId(@Param("storeId") String storeId);
}
