package com.aitem.connect.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
public class OrderRequest implements Serializable {

	private String billingName;
	// TODO :  get from header
	List<OrderItemDetails> itemDetails;
	// TODO: add origin address destination address for driver
}
