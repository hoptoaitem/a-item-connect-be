package com.aitem.connect.repository;

import com.aitem.connect.model.CartModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


public interface CartRepository extends JpaRepository<CartModel, String> {

    CartModel findByUserId(@Param("userId") String userId);

    CartModel findByUserIdAndStatus(@Param("userId") String userId,
                                    @Param("status") String status);

    CartModel findByOrderId(@Param("orderId") String orderId);
}
