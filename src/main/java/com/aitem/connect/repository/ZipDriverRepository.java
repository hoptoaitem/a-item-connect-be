package com.aitem.connect.repository;

import com.aitem.connect.model.ZipDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZipDriverRepository extends JpaRepository<ZipDriver, String> {
    List<ZipDriver> findByZip(@Param("zip") String zip);
}
