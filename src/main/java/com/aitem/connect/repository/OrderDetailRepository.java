package com.aitem.connect.repository;

import com.aitem.connect.model.OrderDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OrderDetailRepository extends JpaRepository<OrderDetailsModel, String> {

    List<OrderDetailsModel> findByDriverId(@Param("driverId") String driverId);
    List<OrderDetailsModel> findByOrderId(@Param("orderId") String orderId);

}
