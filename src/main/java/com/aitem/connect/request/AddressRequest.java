package com.aitem.connect.request;

import lombok.Data;

import java.io.Serializable;


@Data
public class AddressRequest implements Serializable {

	/*
	CREATE TABLE IF NOT EXISTS store (
			id VARCHAR(128) PRIMARY KEY,
	address_id VARCHAR(128),
	retailer_user_id VARCHAR(128),
	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	modified_at DATETIME DEFAULT CURRENT_TIMESTAMP
	*/
	private String addressName;
	private String streetAddress1;
	private String streetAddress;
	private String city;
	private String zip;
	private String state;
}
