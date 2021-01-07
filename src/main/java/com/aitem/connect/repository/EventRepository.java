package com.aitem.connect.repository;

import com.aitem.connect.model.EventModel;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.*;


public interface EventRepository extends Repository<EventModel, String> {
}
