package com.aitem.connect.repository;

import com.aitem.connect.model.AddressModel;
import com.aitem.connect.model.StoreModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AddressRepository extends JpaRepository<AddressModel, String> {

}
