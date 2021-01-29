package com.aitem.connect.request;

import lombok.Data;

import java.io.Serializable;


@Data
public class AddressDetailRequest implements Serializable {
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	private String streetAddress1;
	private String streetAddress;
	private String city;
	private String state;
	private String zip;
}
