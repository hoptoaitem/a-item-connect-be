package com.aitem.connect.repository;

import com.aitem.connect.model.ZipNeighbour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZipNeighbourRepository extends JpaRepository<ZipNeighbour, String> {

    List<ZipNeighbour> findByZip(@Param("zip") String zip);
}
