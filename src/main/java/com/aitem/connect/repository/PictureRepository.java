package com.aitem.connect.repository;

import com.aitem.connect.model.AddressModel;
import com.aitem.connect.model.PictureModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PictureRepository extends JpaRepository<PictureModel, String> {

}
