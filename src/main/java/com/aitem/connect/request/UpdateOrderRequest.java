package com.aitem.connect.request;

import com.aitem.connect.enums.OrderStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class UpdateOrderRequest implements Serializable {

	private String orderId;
	private OrderStatus orderStatus;
}
