package com.aitem.connect.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class EventRequest implements Serializable {
	private String eventName;
}
