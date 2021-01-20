package com.aitem.connect.repository;

import com.aitem.connect.model.EventModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface EventRepository extends JpaRepository<EventModel, String> {
	List<EventModel> findByCreatedBy(@Param("createdBy") String createdBy);
	List<EventModel> findByStatus(@Param("status") Long status);
	EventModel findById(@Param("id") String id);
}
