package com.aitem.connect.repository;

import com.aitem.connect.model.Zip;
import com.aitem.connect.model.ZipDriver;
import com.aitem.connect.model.ZipStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZipStoreRepository extends JpaRepository<ZipStore, String> {

    List<ZipStore> findByZip(@Param("zip")String zip);
}
