package com.aitem.connect.repository;

import com.aitem.connect.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface TransactionRepository extends JpaRepository<TransactionModel, String> {
	List<TransactionModel> findByUserId(@Param("userId") String userId);
}
