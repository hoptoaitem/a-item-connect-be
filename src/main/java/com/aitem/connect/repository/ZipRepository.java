package com.aitem.connect.repository;

import com.aitem.connect.model.User;
import com.aitem.connect.model.Zip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ZipRepository extends JpaRepository<Zip, String> {


}
