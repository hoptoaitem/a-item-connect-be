package com.aitem.connect.response;

import com.aitem.connect.model.AddressDetailModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) //or Include.NON_EMPTY, if that fits your use case
public class TransactionResponse implements Serializable {
	private static final long serialVersionUID = -6955764528854378889L;
	private String id;
	private AddressDetailModel billingAddress;
	private AddressDetailModel deliveryAddress;
	private String itemName;
	private long quantity;
	private String price;
	private String note;
	private Date createdAt;

}
