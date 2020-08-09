package com.aitem.connect.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) //or Include.NON_EMPTY, if that fits your use case
public class LoginResponse implements Serializable {

	private static final long serialVersionUID = -6955764528854378889L;


    private String authToken;
	private String profileType;
	private String username;
}
