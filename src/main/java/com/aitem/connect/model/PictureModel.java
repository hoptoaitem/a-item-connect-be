package com.aitem.connect.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "picture")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_NULL) //or Include.NON_EMPTY, if that fits your use case
public class PictureModel {

	@Id
	private String id;
	private String path;
	private String originalFileName;

	private Date createdAt;
	private Date modifiedAt;
	private String createdBy;
	private String modifiedBy;
}
