package com.aitem.connect.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "user")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class User implements Serializable {
	
	public static final long serialVersionUID = -5659409774140215128L;

	@Id
	private String id;
	private String username;
	private String pass;
	private String iv;
	private String salt;
	private String profileType;
	private String addressId;

	private Date createdAt;
	private Date modifiedAt;
	private String createdBy;
	private String modifiedBy;
}
