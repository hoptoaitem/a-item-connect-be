package com.aitem.connect.repository;

import com.aitem.connect.model.EventModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


public interface EventRepository extends JpaRepository<EventModel, String> {
}
