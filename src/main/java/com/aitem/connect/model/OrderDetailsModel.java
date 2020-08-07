package com.aitem.connect.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "order_detail")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class OrderDetailsModel {

    /*
    CREATE TABLE IF NOT EXISTS order_detail (
            id VARCHAR(128) PRIMARY KEY,
    order_id VARCHAR(128) NOT NULL,
    item_id VARCHAR(128) NOT NULL,
    user_id VARCHAR(128) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    qty INTEGER NOT NULL
)

    */
    @Id
    private String id;
    private String orderId;
    private String itemId;
    private String userId;
    private String driverId;
    @Column(name="qty")
    private Integer quantity;

    private Date createdAt;
    private Date modifiedAt;
    private String createdBy;
    private String modifiedBy;
}
