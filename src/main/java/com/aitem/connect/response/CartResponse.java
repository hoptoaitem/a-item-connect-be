package com.aitem.connect.response;

import com.aitem.connect.enums.CartStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) //or Include.NON_EMPTY, if that fits your use case
public class CartResponse {
    private String id;
    private CartStatus status;
    private String orderId;

    List<ItemResponse> items;

    private String total;
}
