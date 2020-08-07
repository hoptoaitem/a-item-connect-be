package com.aitem.connect.response;

import com.aitem.connect.enums.OrderStatus;
import com.aitem.connect.model.AddressModel;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

@Data
public class OrderResponse {

    private String id;
    @Column(name="order_external_ref_id")
    private String orderExternalReferenceId;
    @Column(name="status")
    private OrderStatus orderStatus;

    private Date createdAt;
    private Date modifiedAt;
    private String createdBy;
    private String modifiedBy;

    // origin
    private AddressModel origin;
    // destination
    private AddressModel destination;


    List<ItemResponse> items;
}
