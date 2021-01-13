package com.aitem.connect.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) //or Include.NON_EMPTY, if that fits your use case
public class EventResponse implements Serializable {
	private static final long serialVersionUID = -6955764528854378889L;
	private String id;
	private String name;
	private String createdBy;
	private String modifiedBy;
	private long status;
	private Date stopAt;
	private Date createdAt;
	private Date modifiedAt;
}
