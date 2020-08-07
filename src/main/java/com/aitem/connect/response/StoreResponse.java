package com.aitem.connect.response;

import com.aitem.connect.model.AddressModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class StoreResponse implements Serializable {

	private static final long serialVersionUID = -6955764528854378889L;


	private String id;
	// TODO make response model separate
	private AddressModel address;
	private String phoneNo;

	private Date createdAt;
	private Date modifiedAt;
	private String createdBy;
	private String modifiedBy;
}
