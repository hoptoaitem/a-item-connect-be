package com.aitem.connect.model;

import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "transaction")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TransactionModel implements Serializable {
    @Id
	private String id;
	private String userId;
	private String orderId;
	private String itemId;
	private String billingAddress;
	private String deliverAddress;
	private long quantity;
	private String price;
	private Date createdAt;
}
