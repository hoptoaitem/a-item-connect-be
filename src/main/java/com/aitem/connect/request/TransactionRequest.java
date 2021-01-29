package com.aitem.connect.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TransactionRequest implements Serializable {
	private AddressRequest billingAddress;
	private AddressRequest deliverAddress;
	private String orderId;
	private String itemId;
	private Integer quantity;
	private String price;
}
