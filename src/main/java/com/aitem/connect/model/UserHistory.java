package com.aitem.connect.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "user_history")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UserHistory implements Serializable {
	
	public static final long serialVersionUID = -5659409774140215128L;

	@Id
	private String id;
	private String userId;
	private Date createdAt;
	private Date modifiedAt;
	private String createdBy;
	private String modifiedBy;
}
