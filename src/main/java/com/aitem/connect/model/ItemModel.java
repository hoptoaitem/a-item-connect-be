package com.aitem.connect.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "item")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_NULL) //or Include.NON_EMPTY, if that fits your use case
public class ItemModel {

	@Id
	private String id;
	private String name;
	private String type;
	private String price;
	private long quantity;
	private String status;
	private String website;
	private String description;
	private String shortDescription;
	private String sku;
	private String weight;
	private String visibility;

	private Date createdAt;
	private Date modifiedAt;
	private String createdBy;
	private String modifiedBy;

	private String storeId;
	private String pictureId;
}
