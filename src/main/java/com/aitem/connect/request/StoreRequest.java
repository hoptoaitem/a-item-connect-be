package com.aitem.connect.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class StoreRequest implements Serializable {

	/*
	CREATE TABLE IF NOT EXISTS store (
			id VARCHAR(128) PRIMARY KEY,
	address_id VARCHAR(128),
	retailer_user_id VARCHAR(128),
	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	modified_at DATETIME DEFAULT CURRENT_TIMESTAMP
)

	*/
	private AddressRequest address;
	// TODO :  get from header
	private String retailerUserId;
	private String phone;
	private String email;
	private String website;
}
