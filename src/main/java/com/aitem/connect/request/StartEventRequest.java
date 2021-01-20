package com.aitem.connect.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class StartEventRequest implements Serializable {
	private String stopAt;
	// private Integer count;
}
