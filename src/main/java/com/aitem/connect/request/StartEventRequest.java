package com.aitem.connect.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class StartEventRequest implements Serializable {
	private String stopAt;
}
