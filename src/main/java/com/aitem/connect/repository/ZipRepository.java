package com.aitem.connect.repository;

import com.aitem.connect.model.Zip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZipRepository extends JpaRepository<Zip, String> {


    List<Zip> findByState(@Param("state")String state);
}
