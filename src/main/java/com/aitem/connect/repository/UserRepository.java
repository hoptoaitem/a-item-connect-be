package com.aitem.connect.repository;

import com.aitem.connect.model.User;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository  extends JpaRepository<User, String> {

	User findByEmail(@Param("email") String email);

    User findByPhone(@Param("phone") String phone);

    List<User> findByProfileType(@Param("profileType") String profileType);
}
