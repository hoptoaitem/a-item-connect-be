package com.aitem.connect.repository;

import com.aitem.connect.model.AddressDetailModel;
import com.aitem.connect.model.StoreModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AddressDetailRepository extends JpaRepository<AddressDetailModel, String> {

}
