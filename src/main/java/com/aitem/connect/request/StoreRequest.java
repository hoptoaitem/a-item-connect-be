package com.aitem.connect.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class StoreRequest implements Serializable {
	private AddressRequest address;
	private String retailer;
	private String phone;
	private String email;
	private String website;
}
