package com.aitem.connect.repository;

import com.aitem.connect.model.RetailerUserModel;
import com.aitem.connect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface RetailerUserRepository extends JpaRepository<RetailerUserModel, String> {
    List<RetailerUserModel> findByUserId(@Param("userId") String userId);
    RetailerUserModel findByStoreId(@Param("storeId") String storeId);
}
