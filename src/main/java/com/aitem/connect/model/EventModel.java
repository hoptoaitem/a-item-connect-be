package com.aitem.connect.model;

import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "event")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class EventModel implements Serializable {
	/*
CREATE TABLE IF NOT EXISTS event (
    id VARCHAR(128) PRIMARY KEY,
    name VARCHAR(128),
    created_by VARCHAR(100),
    modified_by VARCHAR(100),
    status INTEGER NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP
)
		*/

	@Id
	private String id;
	private String name;
	private String createdBy;
	private String modifiedBy;
	private long status;
	private Date createdAt;
	private Date modifiedAt;
}
