package com.aitem.connect.response;

import com.aitem.connect.model.AddressModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) //or Include.NON_EMPTY, if that fits your use case
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
