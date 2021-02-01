package com.aitem.connect.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.aitem.connect.model.User;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) //or Include.NON_EMPTY, if that fits your use case
public class EventUserResponse implements Serializable {
	private static final long serialVersionUID = -6955764528854378889L;
	private User user;
    private long transactCount;
}
