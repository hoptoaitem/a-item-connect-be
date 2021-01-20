package com.aitem.connect.request;

import com.aitem.connect.enums.ItemStatus;
import lombok.Data;

import java.io.Serializable;


@Data
public class ItemRequest implements Serializable {
	private String id;
	private String name;
	private String type;
	private String price;
	private Integer quantity;
	private ItemStatus status;
	private String website;
	private String description;
	private String shortDescription;
	private String sku;
	private String weight;
	private String visibility;

	private String storeId;
	private String pictureId;
	private String pictureUrl;
}
