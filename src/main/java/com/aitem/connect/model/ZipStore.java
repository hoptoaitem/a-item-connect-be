package com.aitem.connect.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "zip_store")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ZipStore implements Serializable {

    public static final long serialVersionUID = -5659409774140215128L;

    /*
CREATE TABLE IF NOT EXISTS zip_neighbour (
    id VARCHAR(128) PRIMARY KEY,
    zip VARCHAR(128) NOT NULL,
    store_id VARCHAR(128) NOT NULL,
    distance VARCHAR(24) NOT NULL,
    created_by VARCHAR(100),
    modified_by VARCHAR(100),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP
)


     */
    @Id
    private String id;
    private String zip;
    private String storeId;
    private String distance;

    private Date createdAt;
    private Date modifiedAt;
    private String createdBy;
    private String modifiedBy;
}
