package com.aitem.connect.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) //or Include.NON_EMPTY, if that fits your use case
public class EventProductsResponse implements Serializable {
	private static final long serialVersionUID = -6955764528854378889L;
	private String id;
    private String name;
    private String type;
    private String price;
    private Integer quantity;
    private String website;
    private String description;
    private String shortDescription;
    private String sku;
    private String weight;
    private String pictureId;
    private Date endDate;
    private String storeName;
    private String eventName;
    private String streetAddress1;
    private String streetAddress;
    private String city;
    private String zip;
    private String state;
}
