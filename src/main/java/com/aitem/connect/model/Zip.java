package com.aitem.connect.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "zip")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Zip implements Serializable {

	/*
	    zip VARCHAR(128) PRIMARY KEY,
    city VARCHAR(128) NOT NULL,
    state VARCHAR(24) NOT NULL,
    lon VARCHAR(100),
    lat VARCHAR(100),
    created_by VARCHAR(100),
    modified_by VARCHAR(100),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP
)
	 */
	public static final long serialVersionUID = -5659409774140215128L;

	@Id
	private String zip;
	private String city;
	private String state;
	private String lon;
	private String lat;


	private Date createdAt;
	private Date modifiedAt;
	private String createdBy;
	private String modifiedBy;
}
