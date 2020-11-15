package com.aitem.connect.request;

import lombok.Data;

import java.io.Serializable;


@Data
public class LoginRequest implements Serializable {
	
	private static final long serialVersionUID = 4583705071842668449L;
	
	private String username;
    private String password;
	private String deviceId;
}
