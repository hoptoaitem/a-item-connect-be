package com.aitem.connect.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "address")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_NULL) //or Include.NON_EMPTY, if that fits your use case
public class AddressDetailModel {
	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	@Column(name="street_address_1")
	private String streetAddress1;
	private String streetAddress;
	private String city;
	private String state;
	private String zip;
	private String createdBy;
	private String modifiedBy;
}
