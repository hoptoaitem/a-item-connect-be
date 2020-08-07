package com.aitem.connect.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginResponse implements Serializable {

	private static final long serialVersionUID = -6955764528854378889L;


    private String authToken;
	private String profileType;
	private String username;
}
