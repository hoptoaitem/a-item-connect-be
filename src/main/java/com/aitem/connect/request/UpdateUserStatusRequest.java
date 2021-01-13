package com.aitem.connect.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateUserStatusRequest implements Serializable {
	private String status;
}
