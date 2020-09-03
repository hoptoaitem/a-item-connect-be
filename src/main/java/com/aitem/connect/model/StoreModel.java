package com.aitem.connect.model;

import com.aitem.connect.enums.OrderStatus;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "store")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class StoreModel implements Serializable {
	/*
CREATE TABLE IF NOT EXISTS store (
  id VARCHAR(128) PRIMARY KEY,
  address_id VARCHAR(128),
  retailer_user_id VARCHAR(128),
  phone_no VARCHAR(128),
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  modified_at DATETIME DEFAULT CURRENT_TIMESTAMP
)

		*/

	@Id
	private String id;
	private String addressId;
	private String phoneNo;
	private String retailerUserId;
	private String email;
	private String website;

	private Date createdAt;
	private Date modifiedAt;
	private String createdBy;
	private String modifiedBy;
}
