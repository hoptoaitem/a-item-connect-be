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
	@Id
	private String id;
	private String name;
	private String createdBy;
	private String modifiedBy;
	private long status;
	private Date stopAt;
	private Date createdAt;
	private Date modifiedAt;
}
