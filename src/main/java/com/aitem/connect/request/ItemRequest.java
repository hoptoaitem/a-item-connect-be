package com.aitem.connect.request;

import com.aitem.connect.enums.ItemStatus;
import lombok.Data;

import java.io.Serializable;


@Data
public class ItemRequest implements Serializable {

	/*
	CREATE TABLE IF NOT EXISTS store (
			id VARCHAR(128) PRIMARY KEY,
	address_id VARCHAR(128),
	retailer_user_id VARCHAR(128),
	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	modified_at DATETIME DEFAULT CURRENT_TIMESTAMP
)

	*/

	private String id;
	private String name;
	private String type;
	private String price;
	private long quantity;
	private ItemStatus status;
	private String website;
	private String description;
	private String shortDescription;
	private String sku;
	private String weight;
	private String visibility;

	private String storeId;
}
