package com.aitem.connect.model;

import com.aitem.connect.enums.CartStatus;
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
public class CartModel implements Serializable {
	/*
CREATE TABLE IF NOT EXISTS cart (
    created_by VARCHAR(100),
    modified_by VARCHAR(100),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(128) NOT NULL
)

		*/

	@Id
	private String id;
	private String orderId;
	@Column(name="status")
	private CartStatus status;
	private String userId;
	private String storeId;
}
