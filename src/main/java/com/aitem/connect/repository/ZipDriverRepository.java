package com.aitem.connect.repository;

import com.aitem.connect.model.ZipDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZipDriverRepository extends JpaRepository<ZipDriver, String> {


}
