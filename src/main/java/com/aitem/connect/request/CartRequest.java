package com.aitem.connect.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class CartRequest implements Serializable {

	private String itemId;
	private Integer quantity;
}
