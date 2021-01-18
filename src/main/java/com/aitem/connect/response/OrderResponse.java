package com.aitem.connect.response;

import com.aitem.connect.enums.OrderStatus;
import com.aitem.connect.model.AddressModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) //or Include.NON_EMPTY, if that fits your use case
public class OrderResponse {

    private String id;
    @Column(name="order_external_ref_id")
    private String orderExternalReferenceId;
    @Column(name="status")
    private OrderStatus orderStatus;
    private String history;

    private Date createdAt;
    private Date modifiedAt;
    private String createdBy;
    private String modifiedBy;

    // origin
    private AddressModel shopAddress;
    // destination
    private AddressModel customerAddress;


    List<ItemResponse> items;

    private Contact contact;
}
