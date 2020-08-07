package com.aitem.connect.request;

import com.aitem.connect.enums.ProfileType;
import lombok.Data;

import java.io.Serializable;


@Data
public class UserRequest implements Serializable {
	
	private static final long serialVersionUID = 4583705071842668449L;
	
	private String username;
    private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private ProfileType profileType;
	private AddressRequest address;
}
