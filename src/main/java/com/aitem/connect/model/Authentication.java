package com.aitem.connect.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "authentication")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Authentication implements Serializable {
	
	public static final long serialVersionUID = -5659409774140215128L;

	@Id
	private String id;
	private String token;
	private String userId;

	private Date createdAt;
	private Date modifiedAt;
	private String createdBy;
	private String modifiedBy;
}