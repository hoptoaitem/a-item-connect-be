package com.aitem.connect.repository;

import com.aitem.connect.model.Authentication;
import com.aitem.connect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


public interface AuthenticationRepository extends JpaRepository<Authentication, Integer> {

	Authentication findByUserId(@Param("userId") String userId);

	Authentication findByToken(@Param("token") String token);
}
