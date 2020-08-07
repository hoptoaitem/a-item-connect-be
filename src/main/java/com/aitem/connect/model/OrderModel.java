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
@Table(name = "shop_order")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class OrderModel implements Serializable {
	/*
	id VARCHAR(128) PRIMARY KEY,
	order_external_ref_id VARCHAR(128) NOT NULL,
	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	modified_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	status VARCHAR(128) NOT NULL
		*/

	@Id
	private String id;
	@Column(name="order_external_ref_id")
	private String orderExternalReferenceId;
	@Column(name="status")
	private OrderStatus orderStatus;
	private String storeId;

	private Date createdAt;
	private Date modifiedAt;
	private String createdBy;
	private String modifiedBy;
}
