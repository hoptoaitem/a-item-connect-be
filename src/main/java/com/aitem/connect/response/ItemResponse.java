package com.aitem.connect.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) //or Include.NON_EMPTY, if that fits your use case
public class ItemResponse {
    private String id;
    private String name;
    private String type;
    private String price;
    private long quantity;
}
